package com.example.tempoapp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://particulier.edf.fr/"

    // Add the following line to use the UnsafeOkHttpClient
    private val client = UnsafeOkHttpClient.instance

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client) // Add this line to use the custom client
        .build()

    val instance: Retrofit
        get() = retrofit
}
