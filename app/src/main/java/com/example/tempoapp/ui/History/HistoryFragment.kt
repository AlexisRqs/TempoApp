package com.example.tempoapp.ui.History

import HistoricTempoAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tempoapp.databinding.FragmentHistoryBinding
import com.example.tempoapp.model.HistoricTempoResponse

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var historicTempoAdapter: HistoricTempoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historicTempoAdapter = HistoricTempoAdapter(listOf())
        binding.historicTempoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.historicTempoRecyclerView.adapter = historicTempoAdapter

        // Observe LiveData objects from ViewModel
        historyViewModel.historicTempo.observe(viewLifecycleOwner) { historicTempoResponse ->
            Log.d("HistoryFragment", "Received historic tempo data: $historicTempoResponse")
            historicTempoResponse?.let { updateHistoricTempoUI(it) }
        }

        historyViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Log.e("HistoryFragment", "Error: $errorMessage")
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        historyViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            // Update UI to show or hide a loading indicator based on the value of isLoading
            binding.loadingIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Fetch the historic tempo data
        val dateBegin = "2022-01-01"
        val dateEnd = "2022-12-31"
        historyViewModel.fetchHistoricTempo(dateBegin, dateEnd)
    }

    private fun updateHistoricTempoUI(historicTempoResponse: List<HistoricTempoResponse>) {
        Log.d("HistoryFragment", "Updating UI with historic tempo data: $historicTempoResponse")
        historicTempoAdapter = HistoricTempoAdapter(historicTempoResponse)
        binding.historicTempoRecyclerView.adapter = historicTempoAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
