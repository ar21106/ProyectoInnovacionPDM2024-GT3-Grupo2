package com.pdm115gt3g2.recetasapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pdm115gt3g2.recetasapp.db.tablas.Pasos

@Dao
interface PasosDao {
    @Insert
    fun insertAll(vararg pasos: Pasos)

    @Query("SELECT * FROM Pasos WHERE idReceta = :idReceta")
    fun getByIdReceta(idReceta: Int): LiveData<List<Pasos>>
}