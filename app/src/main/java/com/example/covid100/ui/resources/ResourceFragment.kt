package com.example.covid100.ui.resources

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
import com.example.covid100.databinding.FragmentResourcesBinding
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions.showToast

class ResourceFragment : Fragment() {

    private var _binding : FragmentResourcesBinding? = null
    private val binding : FragmentResourcesBinding get() = _binding!!

    private lateinit var viewModel: ResourceViewModel

    private lateinit var resourceAdapter: ResourceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResourcesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupViews()
        observeValues()
    }

    private fun setupViewModel() {
        viewModel = ResourceViewModel.getResourceViewModel(this)
    }

    private fun setupViews() {
        setupToolbarMenu()
        setupRecyclerView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllResources()
        }
        binding.fabUpload.setOnClickListener {
            findNavController().navigate(R.id.action_resourceFragment_to_uploadFragment)
        }
    }

    private fun observeValues() {
        viewModel.resources.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    resourceAdapter.submitList(it.data)
                }
                is Result.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    requireContext().showToast("Something went wrong. ${it.errorMsg}")
                }
                is Result.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is Result.EmptySuccess -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    resourceAdapter.submitList(listOf())
                    requireContext().showToast("Sorry, no resources available.")
                }
            }
        }
    }

    private fun setupToolbarMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.resourceAll -> {
                    binding.toolbar.title = it.title
                    true
                }
                R.id.resourceOxygen -> {
                    binding.toolbar.title = it.title
                    true
                }
                R.id.resourceICU -> {
                    binding.toolbar.title = it.title
                    true
                }
                R.id.resourceVenti -> {
                    binding.toolbar.title = it.title
                    true
                }
                R.id.resourceMedicine -> {
                    binding.toolbar.title = it.title
                    true
                }
                R.id.resourceHospital -> {
                    binding.toolbar.title = it.title
                    true
                }
                R.id.resourcePlasma -> {
                    binding.toolbar.title = it.title
                    true
                }
                else -> false
            }
        }
    }

    private fun setupRecyclerView() {
        resourceAdapter = ResourceAdapter(
            {
                Log.d("ResourcesFragment", "Item clicked: $it")
                findNavController().navigate(
                    R.id.action_resourceFragment_to_resourceDetailBottomSheetFragment,
                    bundleOf(
                        "id" to it
                    )
                )
            },
            { id, up, down, isLike, toggle ->
                viewModel.likeDislikeResource(id, up, down, isLike, toggle)
            }
        )
        binding.rvResources.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = resourceAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}