package com.example.tempoapp

import com.example.tempoapp.model.ColorTempoResponse
import com.example.tempoapp.model.HistoricTempoResponse
import com.example.tempoapp.model.RemainingTempoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IEdfApi {
    companion object {
        const val EDF_TEMPO_API_ALERT_TYPE = "TEMPO"
    }


    // https://particulier.edf.fr/services/rest/referentiel/searchTempoStore?dateRelevant=2022-12-05&TypeAlerte=TEMPO
    @GET("services/rest/referentiel/searchTempoStore")
    fun getColorTempo(
        @Query("dateRelevant") dateBegin : String,
        @Query("TypeAlerte") alertType : String
    ): Call<ColorTempoResponse>

    // https://particulier.edf.fr/services/rest/referentiel/getNbTempoDays?TypeAlerte=TEMPO
    @GET("services/rest/referentiel/getNbTempoDays")
    fun getRemainingTempo(
        @Query("TypeAlerte") alertType : String
    ): Call<RemainingTempoResponse>

    // https://particulier.edf.fr/services/rest/referentiel/historicTEMPOStore?dateBegin=2020&dateEnd=2021
    @GET("services/rest/referentiel/historicTEMPOStore")
    fun getHistoricTempo(
        @Query("dateBegin") dateBegin : String,
        @Query("dateEnd") dateEnd : String
    ): Call<HistoricTempoResponse>
}