package com.pdm115gt3g2.recetasapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.pdm115gt3g2.recetasapp.db.tablas.Ingredientes

@Dao
interface IngredientesDao{
    @Insert
    fun insertAll(vararg ingredientes: Ingredientes)
}