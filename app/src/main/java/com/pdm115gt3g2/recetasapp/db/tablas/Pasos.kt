package com.pdm115gt3g2.recetasapp.db.tablas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pasos(
    val idReceta: Int,
    val titulo: String,
    val descripcion: String,
    val tiempoSegundos: Int,
    val completado: Boolean = false,

    @PrimaryKey(autoGenerate = true)
    val idPaso: Int = 0
)
