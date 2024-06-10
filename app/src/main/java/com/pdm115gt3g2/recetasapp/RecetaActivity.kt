package com.pdm115gt3g2.recetasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.pdm115gt3g2.recetasapp.adapters.IngredientesAdapter
import com.pdm115gt3g2.recetasapp.db.RecetasAppDb
import com.pdm115gt3g2.recetasapp.db.Repositorio
import com.pdm115gt3g2.recetasapp.db.tablas.Ingredientes
import com.pdm115gt3g2.recetasapp.db.tablas.Recetas
import com.pdm115gt3g2.recetasapp.model.RecetasResponse
import com.pdm115gt3g2.recetasapp.retrofit.ApiService
import com.pdm115gt3g2.recetasapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class RecetaActivity : AppCompatActivity() {

    private lateinit var idReceta: TextView
    private lateinit var nombre: TextView
    private lateinit var descripcion: TextView
    private lateinit var favorito: SwitchCompat
    private lateinit var btnComenzar: Button
    private lateinit var btnSubir: Button

    lateinit var db: RecetasAppDb
    lateinit var repositorio: Repositorio
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_receta)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        idReceta = findViewById(R.id.txt_receta_id_form)
        nombre = findViewById(R.id.txt_receta_nombre_form)
        descripcion = findViewById(R.id.txt_receta_descripcion_form)
        favorito = findViewById(R.id.swicth_receta_favorito)
        btnComenzar = findViewById(R.id.btn_empezar_receta)
        btnSubir = findViewById(R.id.btn_subir_receta)

        //llenando el formulario
        val parametros = intent.extras
        val idTmp = parametros?.getString("id")?:""
        val nombreTmp = parametros?.getString("nombre")?:""
        val descripcionTmp = parametros?.getString("descripcion")?:""
        val favoritoTmp = parametros?.getString("favorito")?:""

        if (favoritoTmp == "Favoritos") favorito.isChecked = true
        idReceta.text = idTmp
        nombre.text = nombreTmp
        descripcion.text = descripcionTmp

        //para usar el recycle view
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView_ingredientes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //acceder a la db usando el repositorio
        db = RecetasAppDb.getDatabase(applicationContext)
        repositorio = Repositorio(db.recetasDao(), db.pasosDao(), db.ingredientesDao())

        val ingredientes = MutableLiveData<List<Ingredientes>>()
        repositorio.getIngredientesByIdReceta(idTmp.toInt()).observeForever{ingrediente->
            ingredientes.postValue(ingrediente ?: emptyList())
        }

        val adapter = IngredientesAdapter(listOf())
        ingredientes.observeForever{ingrediente->
            ingrediente?.let {
                adapter.updateItems(it)
            } ?: run{
                adapter.updateItems(emptyList())
            }
        }

        recyclerView.adapter = adapter

        //actualizar favorito
        favorito.setOnClickListener{
            actualizarFavorito()
        }

        //boton comenzar a ver los pasos
        btnComenzar.setOnClickListener{
            val intent = Intent(this,PasosActivity::class.java)
            intent.putExtra("idReceta",idReceta.text)
            this.startActivity(intent)
        }

        //boton subir a la nube
        btnSubir.setOnClickListener {
            subirReceta()
        }


    }

    private fun actualizarFavorito(){
        val receta = Recetas(nombre.text.toString(),descripcion.text.toString(),favorito.isChecked,idReceta.text.toString().toInt())
        db = RecetasAppDb.getDatabase(applicationContext)
        repositorio = Repositorio(db.recetasDao(),db.pasosDao(), db.ingredientesDao())
        repositorio.updateReceta(receta)
    }

    private fun subirReceta(){
        val nom = nombre.text.toString()
        val descr = descripcion.text.toString()
        val favor = favorito.isChecked.toString()

        lifecycleScope.launch {
            try{
                val call = RetrofitClient().getRetrofit().create(ApiService::class.java).insertReceta(nom, descr, favor)
                if (call.isSuccessful){
                    val responseBody = call.body()
                    Toast.makeText(applicationContext, responseBody?.mensaje, Toast.LENGTH_SHORT).show()

                }else{
                    val errorBody = call.errorBody()?.string()
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, RecetasResponse::class.java)
                    Toast.makeText(applicationContext, errorResponse.mensaje, Toast.LENGTH_SHORT).show()
                }

            }catch (e: Exception) {
                Toast.makeText(applicationContext, "Error: "+e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}