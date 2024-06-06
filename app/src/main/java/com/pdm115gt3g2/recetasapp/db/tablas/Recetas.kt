package com.pdm115gt3g2.recetasapp.db.tablas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recetas(
    val nombre: String,
    val descripcion: String,
    val esFavorito: Int,

    @PrimaryKey(autoGenerate = true)
    val idReceta: Int = 0
)