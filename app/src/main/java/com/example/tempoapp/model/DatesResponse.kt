package com.example.tempoapp.model

import com.google.gson.annotations.SerializedName

data class DatesResponse(
    @SerializedName("dates")
    val dates: List<HistoricTempoResponse>
)
