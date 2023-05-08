package com.example.tempoapp.model

import com.google.gson.annotations.SerializedName

data class HistoricTempoResponse(
    @SerializedName("date")
    val date: String? = null,

    @SerializedName("couleur")
    val couleur: String? = null
)
