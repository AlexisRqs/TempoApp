package com.example.tempoapp.ui.Home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tempoapp.databinding.FragmentHomeBinding
import com.example.tempoapp.model.ColorTempoResponse
import com.example.tempoapp.model.HistoricTempoResponse
import com.example.tempoapp.model.RemainingTempoResponse

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val LOGTAG = HomeFragment::class.simpleName

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

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

        // Observe LiveData objects from ViewModel
        homeViewModel.tempoColor.observe(viewLifecycleOwner) { colorTempoResponse ->
            colorTempoResponse?.let { updateColorTempoUI(it) }
        }

        homeViewModel.remainingTempo.observe(viewLifecycleOwner) { remainingTempoResponse ->
            remainingTempoResponse?.let { updateRemainingTempoUI(it) }
        }

        homeViewModel.historicTempo.observe(viewLifecycleOwner) { historicTempoResponse ->
            historicTempoResponse?.let { updateHistoricTempoUI(it) }
        }

        homeViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Log.e(LOGTAG, it)
                // Show an error message to the user
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Update UI to show or hide a loading indicator based on the value of isLoading
        }

        fetchColorTempo()
        fetchRemainingTempo()
        fetchHistoricTempo()
    }

    private fun fetchColorTempo() {
        // Use the current date for the date parameter
        val currentDate = java.text.SimpleDateFormat("dd-MM-yyyy").format(java.util.Date())
        homeViewModel.fetchTempoColor(currentDate)
    }

    private fun fetchRemainingTempo() {
        homeViewModel.fetchRemainingTempo()
    }

    private fun fetchHistoricTempo() {
        // Define the date range you want to fetch the historic data for
        val dateBegin = "2022-01-01"
        val dateEnd = "2022-12-31"
        homeViewModel.fetchHistoricTempo(dateBegin, dateEnd)
    }

    private fun updateColorTempoUI(colorTempoResponse: ColorTempoResponse) {
        binding.textViewTempoToday.text = "Tempo du jour : ${colorTempoResponse.todayColor}"
        binding.textViewTempoTomorrow.text = "Tempo de demain : ${colorTempoResponse.tomorrowColor}"
    }

    private fun updateRemainingTempoUI(remainingTempoResponse: RemainingTempoResponse) {
        val remainingTempoText = "Tempo restants : Bleu : ${remainingTempoResponse.PARAMNBJBLEU} jours, Blanc : ${remainingTempoResponse.PARAMNBJBLANC} jours, Rouge : ${remainingTempoResponse.PARAMNBJROUGE} jours"
        binding.textViewRemainingTempo.text = remainingTempoText
    }

    private fun updateHistoricTempoUI(historicTempoResponseList: List<HistoricTempoResponse>) {
        // Update the UI with the historic tempo data, you can format the data as needed.
        val historicTempoText = historicTempoResponseList.joinToString(separator = "\n") { "${it.date}: ${it.couleur}" }
        binding.textViewHistoricTempo.text = historicTempoText
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
