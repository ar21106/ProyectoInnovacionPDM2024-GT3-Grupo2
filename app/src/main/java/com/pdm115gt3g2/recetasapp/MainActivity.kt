package com.pdm115gt3g2.recetasapp

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.pdm115gt3g2.recetasapp.adapters.RecetasAdapter
import com.pdm115gt3g2.recetasapp.db.LlenarDBWorker
import com.pdm115gt3g2.recetasapp.db.RecetasAppDb
import com.pdm115gt3g2.recetasapp.db.Repositorio
import com.pdm115gt3g2.recetasapp.db.tablas.Recetas

class MainActivity : AppCompatActivity() {

    lateinit var db: RecetasAppDb
    lateinit var repositorio: Repositorio
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //llenando base de datos usando el worker
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isTestDataInserted = sharedPreferences.getBoolean("is_test_data_inserted", false)
        if (!isTestDataInserted) {
            val workRequest = OneTimeWorkRequestBuilder<LlenarDBWorker>().build()
            WorkManager.getInstance(this).enqueue(workRequest)
            sharedPreferences.edit().putBoolean("is_test_data_inserted", true).apply()
        }

        //para usar el recycle view
        val recyclerView = findViewById<RecyclerView>(R.id.recycleView_recetas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //acceder a la db usando el repositorio
        db = RecetasAppDb.getDatabase(applicationContext)
        repositorio = Repositorio(db.recetasDao(), db.pasosDao(), db.ingredientesDao())

        val recetas = MutableLiveData<List<Recetas>>()
        repositorio.getRecetas().observeForever{receta->
            recetas.postValue(receta ?: emptyList())
        }

        val adapter = RecetasAdapter(listOf())
        recetas.observeForever{receta->
            receta?.let {
                adapter.updateItems(it)
            } ?: run{
                adapter.updateItems(emptyList())
            }
        }

        recyclerView.adapter = adapter
    }
}