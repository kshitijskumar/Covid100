package com.example.covid100.ui.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.covid100.R
import com.example.covid100.databinding.FragmentUploadBinding
import com.example.covid100.ui.resources.ResourceViewModel
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions.showToast

class UploadFragment : Fragment() {

    private var _binding : FragmentUploadBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ResourceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        observeValues()
        setupViews()
    }

    private fun setupViewModel() {
        viewModel = ResourceViewModel.getResourceViewModel(this, false)
    }

    private fun setupViews() {
        setupResourceTypeDropDown()
        binding.btnUpload.setOnClickListener {
            val name = binding.etName.text.toString()
            val contact = binding.etContact.text.toString()
            val resourceString = binding.etResourceType.text.toString()
            val msg = binding.etMsg.text.toString()

            viewModel.uploadResource(name, contact, resourceString, msg)
        }
    }

    private fun setupResourceTypeDropDown() {
        val resourcesList = resources.getStringArray(R.array.resources_type_list)
        val resourcesAdapter = ArrayAdapter(requireContext(), R.layout.holder_resources_type_dropdown, resourcesList)

        binding.etResourceType.setAdapter(resourcesAdapter)
    }

    private fun observeValues() {
        viewModel.uploadStatus.observe(viewLifecycleOwner) {
            when(it) {
                is Result.EmptySuccess -> {
                    hideLoading()
                    requireContext().showToast("Thank you for your valuable contribution")
                    findNavController().navigateUp()
                }
                is Result.Error -> {
                    hideLoading()
                    requireContext().showToast(it.errorMsg)
                }
                is Result.Loading -> {
                    showLoading()
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            pbLoading.visibility = View.VISIBLE
            btnUpload.isEnabled = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            pbLoading.visibility = View.GONE
            btnUpload.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}