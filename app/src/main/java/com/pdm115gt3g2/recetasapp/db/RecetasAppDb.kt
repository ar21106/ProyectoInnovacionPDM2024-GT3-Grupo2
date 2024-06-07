package com.pdm115gt3g2.recetasapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pdm115gt3g2.recetasapp.db.dao.IngredientesDao
import com.pdm115gt3g2.recetasapp.db.dao.PasosDao
import com.pdm115gt3g2.recetasapp.db.dao.RecetasDao
import com.pdm115gt3g2.recetasapp.db.tablas.Ingredientes
import com.pdm115gt3g2.recetasapp.db.tablas.Pasos
import com.pdm115gt3g2.recetasapp.db.tablas.Recetas

@Database(entities = [Recetas::class, Pasos::class, Ingredientes::class], version = 1)
abstract class RecetasAppDb: RoomDatabase(){
    abstract fun recetasDao(): RecetasDao
    abstract fun pasosDao(): PasosDao
    abstract fun ingredientesDao(): IngredientesDao

    companion object {
        @Volatile
        private var INSTANCE: RecetasAppDb? = null

        fun getDatabase(context: Context): RecetasAppDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecetasAppDb::class.java,
                    "RecetasAppDb"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}