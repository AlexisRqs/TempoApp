package com.example.tempoapp.model

import com.google.gson.annotations.SerializedName

data class ColorTempoResponse(
    @SerializedName("couleurJourJ")
    val todayColor: String? = null,

    @SerializedName("couleurJourJ1")
    val tomorrowColor: String? = null,

    @SerializedName("remainingBlue")
    val remainingBlue: Int? = null,

    @SerializedName("remainingWhite")
    val remainingWhite: Int? = null,

    @SerializedName("remainingRed")
    val remainingRed: Int? = null
)
