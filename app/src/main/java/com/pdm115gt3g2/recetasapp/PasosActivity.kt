package com.pdm115gt3g2.recetasapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdm115gt3g2.recetasapp.adapters.PasosAdapter
import com.pdm115gt3g2.recetasapp.db.RecetasAppDb
import com.pdm115gt3g2.recetasapp.db.Repositorio
import com.pdm115gt3g2.recetasapp.db.tablas.Pasos

class PasosActivity : AppCompatActivity() {

    lateinit var db: RecetasAppDb
    lateinit var repositorio: Repositorio
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pasos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //parametros
        val parametros = intent.extras
        val idReceta = parametros?.getString("idReceta")?:""

        //para usar el recycle view
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView_pasos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //acceder a la db usando el repositorio
        db = RecetasAppDb.getDatabase(applicationContext)
        repositorio = Repositorio(db.recetasDao(), db.pasosDao())

        val pasos = MutableLiveData<List<Pasos>>()
        repositorio.getPasosByIdReceta(idReceta.toInt()).observeForever{paso->
            pasos.postValue(paso ?: emptyList())
        }

        val adapter = PasosAdapter(listOf())
        pasos.observeForever{paso->
            paso?.let {
                adapter.updateItems(it)
            } ?: run{
                adapter.updateItems(emptyList())
            }
        }

        recyclerView.adapter = adapter
    }
}