package com.pdm115gt3g2.recetasapp.db.tablas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredientes(
    val idReceta: Int,
    val uMedida: String,
    val nombre: String,
    val cantidad: Double,

    @PrimaryKey(autoGenerate = true)
    val idIngrediente: Int = 0
)
