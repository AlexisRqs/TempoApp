package com.example.tempoapp.ui.Home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tempoapp.ApiClient
import com.example.tempoapp.IEdfApi
import com.example.tempoapp.model.ColorTempoResponse
import com.example.tempoapp.model.RemainingTempoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val edfApi = ApiClient.instance.create(IEdfApi::class.java)

    private val _text = MutableLiveData<String>().apply {
        value = "Passez une bonne journée ! :)"
    }
    val text: LiveData<String> = _text

    val tempoColor = MutableLiveData<ColorTempoResponse?>()
    val remainingTempo = MutableLiveData<RemainingTempoResponse?>()

    val error = MutableLiveData<String?>()
    val isLoading = MutableLiveData<Boolean>()

    fun fetchTempoColor(date: String) {
        isLoading.value = true
        edfApi.getColorTempo(date, IEdfApi.EDF_TEMPO_API_ALERT_TYPE).enqueue(object : Callback<ColorTempoResponse> {
            override fun onResponse(call: Call<ColorTempoResponse>, response: Response<ColorTempoResponse>) {
                if (response.isSuccessful) {
                    tempoColor.value = response.body()
                } else {
                    error.value = "Error fetching tempo color"
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<ColorTempoResponse>, t: Throwable) {
                error.value = "Error fetching tempo color: ${t.localizedMessage}"
                isLoading.value = false
            }
        })
    }

    fun fetchRemainingTempo() {
        isLoading.value = true
        edfApi.getRemainingTempo(IEdfApi.EDF_TEMPO_API_ALERT_TYPE).enqueue(object : Callback<RemainingTempoResponse> {
            override fun onResponse(call: Call<RemainingTempoResponse>, response: Response<RemainingTempoResponse>) {
                if (response.isSuccessful) {
                    remainingTempo.value = response.body()
                } else {
                    error.value = "Error fetching remaining tempo"
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<RemainingTempoResponse>, t: Throwable) {
                error.value = "Error fetching remaining tempo: ${t.localizedMessage}"
                isLoading.value = false
            }
        })
    }

}
