package com.pdm115gt3g2.recetasapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pdm115gt3g2.recetasapp.db.RecetasAppDb
import com.pdm115gt3g2.recetasapp.db.Repositorio
import com.pdm115gt3g2.recetasapp.db.tablas.Recetas

class RecetaActivity : AppCompatActivity() {

    private lateinit var idReceta: TextView
    private lateinit var nombre: TextView
    private lateinit var descripcion: TextView
    private lateinit var favorito: SwitchCompat
    private lateinit var btnComenzar: Button

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

        //llenando el formulario
        val parametros = intent.extras
        val idTmp = parametros?.getString("id")?:""
        val nombreTmp = parametros?.getString("nombre")?:""
        val descripcionTmp = parametros?.getString("descripcion")?:""
        val favoritoTmp = parametros?.getString("favorito")?:""

        var fav = true
        if (favoritoTmp == "") fav = false

        idReceta.setText(idTmp)
        nombre.setText(nombreTmp)
        descripcion.setText(descripcionTmp)
        favorito.isChecked = fav

        //actualizar favorito
        favorito.setOnClickListener{
            val receta = Recetas(nombreTmp,descripcionTmp,favorito.isChecked,idTmp.toInt())
            db = RecetasAppDb.getDatabase(applicationContext)
            repositorio = Repositorio(db.recetasDao(),db.pasosDao())
            repositorio.updateReceta(receta)
        }

        //boton comenzar a ver los pasos
        btnComenzar.setOnClickListener{
            val intent = Intent(this,PasosActivity::class.java)
            intent.putExtra("idReceta",idReceta.text)
            this.startActivity(intent)
        }


    }
}