package com.pdm115gt3g2.recetasapp.db.tablas

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Medidas(
    val nombre: String,

    @PrimaryKey(autoGenerate = true)
    val idMedida: Int = 0
)
