package com.example.tempoapp

import com.example.tempoapp.model.ColorTempo
import com.example.tempoapp.model.HistoricTempo
import com.example.tempoapp.model.RemainingTempo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IEdfApi {






    @GET("searchTempoStore?dateRelevant=2023-02-09&TypeAlerte=TEMPO")
    fun getColorTempo(): Call<ColorTempo>

    @GET("getNbTempoDays?TypeAlerte=TEMPO")
    fun getRemainingTempo(): Call<RemainingTempo>

    @GET("historicTEMPOStore?dateBegin=2022&dateEnd=2023")
    fun getHistoricTempo(): Call<HistoricTempo>
}