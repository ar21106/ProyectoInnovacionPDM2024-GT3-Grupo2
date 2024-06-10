package com.pdm115gt3g2.recetasapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.pdm115gt3g2.recetasapp.db.tablas.Ingredientes

@Dao
interface IngredientesDao{
    @Insert
    fun insertAll(vararg ingredientes: Ingredientes)

    @Query("SELECT * FROM Ingredientes WHERE idReceta = :idReceta")
    fun getByIdReceta(idReceta: Int): LiveData<List<Ingredientes>>
}