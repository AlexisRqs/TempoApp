package com.example.tempoapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        const val EDFAPI_BASE_URL = "https://particulier.edf.fr/services/rest/referentiel/"

        // keep singleton instance
        val instance = Build()

        private fun Build(): Retrofit {

            // GSON converter creation
            val converter = GsonConverterFactory.create()

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
            // HTTP Client

            val okHttpClient = OkHttpClient().newBuilder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl(EDFAPI_BASE_URL)
                .addConverterFactory(converter)
                .client(okHttpClient)
                .build()
        }
    }

}