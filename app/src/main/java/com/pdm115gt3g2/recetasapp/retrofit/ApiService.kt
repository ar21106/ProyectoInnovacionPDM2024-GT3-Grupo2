package com.pdm115gt3g2.recetasapp.retrofit

import com.pdm115gt3g2.recetasapp.model.RecetasResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("Recetas")
    suspend fun insertReceta(
        @Field("nombre") nombre: String,
        @Field("descripcion") descripcion: String,
        @Field("esFavorito") esFavorito: String
    ):Response<RecetasResponse>

}