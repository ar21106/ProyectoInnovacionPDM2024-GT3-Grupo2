package com.pdm115gt3g2.recetasapp.model

import com.google.gson.annotations.SerializedName

data class Receta(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("esFavorito") val esFavorito: String
)

data class RecetasResponse(
    @SerializedName("Estado") val estado: String,
    @SerializedName("Mensaje") val mensaje: String,
    @SerializedName("data") val data: List<Receta>
)