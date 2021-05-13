package com.example.covid100.ui.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid100.data.response.CovidStatSingleResponse
import com.example.covid100.databinding.FragmentStatsBinding
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions.showToast

class StatsFragment : Fragment() {

    private var _binding : FragmentStatsBinding? = null
    private val binding : FragmentStatsBinding get() = _binding!!

    private lateinit var viewModel: StatsViewModel

    private lateinit var statAdapter: CovidStatsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupViews()
        observeValues()
    }

    private fun setupViewModel() {
        viewModel = StatsViewModel.getStatsViewModel(this)
    }

    private fun setupViews() {
        statAdapter = CovidStatsAdapter()
        binding.rvStatesCases.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = statAdapter
            isNestedScrollingEnabled = true
        }
    }

    private fun observeValues() {
        viewModel.covidCases.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    hideLoading()
                    populateCovidCard(it.data.totalValues)
                    statAdapter.submitList(it.data.stateWise?.listOfStatesData)
                }
                is Result.Loading -> {
                    showLoading()
                }
                is Result.Error -> {
                    hideLoading()
                    requireContext().showToast(it.errorMsg)
                }
            }
        }
    }

    private fun populateCovidCard(info: CovidStatSingleResponse?) {
        binding.tvUpdatedTime.text = info?.lastupdatedtime
        binding.ccConfirmed.apply {
            setCount(info?.confirmed)
            setDelta(info?.deltaconfirmed)
        }
        binding.ccActive.apply {
            setCount(info?.active)
        }
        binding.ccRecovered.apply {
            setCount(info?.recovered)
            setDelta(info?.deltarecovered)
        }
        binding.ccDeath.apply {
            setCount(info?.deaths)
            setDelta(info?.deltadeaths)
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}