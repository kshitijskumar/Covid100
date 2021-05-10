package com.example.covid100.ui.help

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid100.R
import com.example.covid100.databinding.FragmentHelpBinding
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions.showToast

class HelpFragment : Fragment() {

    private var _binding : FragmentHelpBinding? = null
    private val binding : FragmentHelpBinding get() = _binding!!

    private lateinit var viewModel: HelpViewModel

    private lateinit var helpAdapter : HelpAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupViews()
        observeValues()
    }

    private fun setupViewModel() {
        viewModel = HelpViewModel.getHelpViewModel(this, true)
    }

    private fun setupViews() {
        setupRecyclerView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllHelpRequests()
        }
        binding.fabRequestHelp.setOnClickListener {
            findNavController().navigate(R.id.action_helpFragment_to_uploadHelpFragment)
        }
    }

    private fun setupRecyclerView() {
        helpAdapter = HelpAdapter {
            Log.d("HelpFragment", "Item click is: $it")
            findNavController().navigate(
                R.id.action_helpFragment_to_helpDetailBottomSheetFragment,
                bundleOf(
                    "id" to it
                )
            )
        }

        binding.rvHelp.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = helpAdapter
        }
    }

    private fun observeValues() {
        viewModel.allHelpRequests.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is Result.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    requireContext().showToast(it.errorMsg)
                }
                is Result.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    helpAdapter.submitList(it.data)
                }
                is Result.EmptySuccess -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    requireContext().showToast("No information found. Keep looking for people to help.")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}