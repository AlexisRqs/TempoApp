package com.example.tempoapp.model

import com.google.gson.annotations.SerializedName

class HistoricTempo {
    @SerializedName("date"    ) var date    : String? = null
    @SerializedName("couleur" ) var couleur : String? = null
}