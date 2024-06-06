package com.pdm115gt3g2.recetasapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pdm115gt3g2.recetasapp.db.dao.IngredientesDao
import com.pdm115gt3g2.recetasapp.db.dao.MedidasDao
import com.pdm115gt3g2.recetasapp.db.dao.PasosDao
import com.pdm115gt3g2.recetasapp.db.dao.RecetasDao
import com.pdm115gt3g2.recetasapp.db.tablas.Ingredientes
import com.pdm115gt3g2.recetasapp.db.tablas.Medidas
import com.pdm115gt3g2.recetasapp.db.tablas.Pasos
import com.pdm115gt3g2.recetasapp.db.tablas.Recetas

@Database(entities = [Recetas::class, Pasos::class, Ingredientes::class, Medidas::class], version = 1)
abstract class RecetasAppDb: RoomDatabase(){
    abstract fun recetasDao(): RecetasDao
    abstract fun pasosDao(): PasosDao
    abstract fun ingredientesDao(): IngredientesDao
    abstract fun medidasDao(): MedidasDao
}