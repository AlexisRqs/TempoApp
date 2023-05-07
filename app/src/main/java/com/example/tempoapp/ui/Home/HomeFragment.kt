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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val LOGTAG = HomeFragment::class.simpleName

    // This property is only valid between onCreateView and
    // onDestroyView.
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
            colorTempoResponse?.let { updateUI(it) }
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
    }

    private fun fetchColorTempo() {
        // Use the current date for the date parameter
        val currentDate = java.text.SimpleDateFormat("yyyy-MM-dd").format(java.util.Date())
        homeViewModel.fetchTempoColor(currentDate)
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
