package com.example.tempoapp.ui.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tempoapp.ApiClient
import com.example.tempoapp.IEdfApi
import com.example.tempoapp.databinding.FragmentHomeBinding
import com.example.tempoapp.model.ColorTempoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val LOGTAG = HomeFragment::class.simpleName

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchColorTempo()
    }

    private fun fetchColorTempo() {
        val edfapi = ApiClient.instance.create(IEdfApi::class.java)
        val EDF_TEMPO_API_ALERT_TYPE = ""
        val call = edfapi.getColorTempo(EDF_TEMPO_API_ALERT_TYPE, String())

        call.enqueue(object : Callback<ColorTempoResponse> {
            override fun onResponse(
                call: Call<ColorTempoResponse>,
                response: Response<ColorTempoResponse>
            ) {
                Log.d(LOGTAG, response.body().toString())
                response.body()?.let { updateUI(it) }
            }

            override fun onFailure(call: Call<ColorTempoResponse>, t: Throwable) {
                Log.e(LOGTAG, "Call to getColorTempo failed")
                // Handle the error and show a message to the user
            }
        })
    }

    private fun updateUI(colorTempoResponse: ColorTempoResponse) {
        binding.textView2.text = "Today's Tempo: ${colorTempoResponse.todayColor}"
        binding.textView3.text = "Tomorrow's Tempo: ${colorTempoResponse.tomorrowColor}"
        binding.textViewTempoRemaining.text = "Remaining days: Blue: ${colorTempoResponse.remainingBlue}, White: ${colorTempoResponse.remainingWhite}, Red: ${colorTempoResponse.remainingRed}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
