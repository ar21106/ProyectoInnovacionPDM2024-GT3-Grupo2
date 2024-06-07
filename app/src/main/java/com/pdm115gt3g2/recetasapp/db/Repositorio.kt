package com.pdm115gt3g2.recetasapp.db

import androidx.lifecycle.LiveData
import com.pdm115gt3g2.recetasapp.db.dao.PasosDao
import com.pdm115gt3g2.recetasapp.db.dao.RecetasDao
import com.pdm115gt3g2.recetasapp.db.tablas.Pasos
import com.pdm115gt3g2.recetasapp.db.tablas.Recetas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
    - Este respositorio sirve para usar todos los dao
      como funciones de una misma clase

    - Además se aprovecha para meter las querys que no retornan data
      (como insert, update, delete) dentro de Coroutines para que no se ejecuten
      en el hilo principal de la app
 */
class Repositorio(
    private var recetasDao: RecetasDao,
    private var pasosDao: PasosDao
){
    fun getRecetas(): LiveData<List<Recetas>>{
        return recetasDao.getAll()
    }

    fun getPasosByIdReceta(idReceta: Int): LiveData<List<Pasos>>{
        return pasosDao.getByIdReceta(idReceta)
    }

    fun insertReceta(receta: Recetas){
        CoroutineScope(Dispatchers.IO).launch{
            recetasDao.insertAll(receta)
        }
    }

    fun updateReceta(receta: Recetas){
        CoroutineScope(Dispatchers.IO).launch {
            recetasDao.updateAll(receta)
        }
    }

    fun updatePaso(paso: Pasos){
        CoroutineScope(Dispatchers.IO).launch{
            pasosDao.updateAll(paso)
        }
    }
}