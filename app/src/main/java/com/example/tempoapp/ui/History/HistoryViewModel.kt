package com.example.tempoapp.ui.History

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tempoapp.ApiClient
import com.example.tempoapp.IEdfApi
import com.example.tempoapp.model.DatesResponse
import com.example.tempoapp.model.HistoricTempoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {

    private val edfApi = ApiClient.instance.create(IEdfApi::class.java)

    private val _historicTempo = MutableLiveData<List<HistoricTempoResponse>>()
    val historicTempo: LiveData<List<HistoricTempoResponse>> = _historicTempo

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun fetchHistoricTempo(dateBegin: String, dateEnd: String) {
        _isLoading.value = true
        edfApi.getHistoricTempo(dateBegin, dateEnd).enqueue(object : Callback<DatesResponse> {
            override fun onResponse(call: Call<DatesResponse>, response: Response<DatesResponse>) {
                if (response.isSuccessful) {
                    _historicTempo.value = response.body()?.dates
                } else {
                    _error.value = "Error fetching historic tempo"
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<DatesResponse>, t: Throwable) {
                _error.value = "Error fetching historic tempo: ${t.localizedMessage}"
                _isLoading.value = false
            }
        })
    }
}
