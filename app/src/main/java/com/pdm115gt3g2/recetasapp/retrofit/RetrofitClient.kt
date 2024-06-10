package com.pdm115gt3g2.recetasapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    public fun getRetrofit():Retrofit{
        val cliente = Retrofit.Builder()
            .baseUrl("http://192.168.0.4/RecetasAppApi/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return cliente
    }
}