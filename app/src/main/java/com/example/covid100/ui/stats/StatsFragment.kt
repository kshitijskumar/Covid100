package com.example.covid100.ui.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.covid100.databinding.FragmentStatsBinding
import com.example.covid100.utils.Result

class StatsFragment : Fragment() {

    private var _binding : FragmentStatsBinding? = null
    private val binding : FragmentStatsBinding get() = _binding!!

    private lateinit var viewModel: StatsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        observeValues()
    }

    private fun setupViewModel() {
        viewModel = StatsViewModel.getStatsViewModel(this)
    }

    private fun observeValues() {
        viewModel.covidCases.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    Log.d("StatsFragment", "List of cases is: ${it.data.stateWise?.listOfStatesData}")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}