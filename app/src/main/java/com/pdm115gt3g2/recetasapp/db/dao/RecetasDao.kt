package com.pdm115gt3g2.recetasapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pdm115gt3g2.recetasapp.db.tablas.Recetas

@Dao
interface RecetasDao {
    @Insert
    fun insertAll(vararg recetas: Recetas)

    @Query("SELECT * FROM Recetas")
    fun getAll(): LiveData<List<Recetas>>

    @Update
    fun updateAll(vararg recetas: Recetas)
}