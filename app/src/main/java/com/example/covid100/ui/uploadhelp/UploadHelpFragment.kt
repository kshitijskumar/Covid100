package com.example.covid100.ui.uploadhelp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.covid100.R
import com.example.covid100.databinding.FragmentUploadHelpBinding
import com.example.covid100.ui.help.HelpViewModel
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions.showToast

class UploadHelpFragment : Fragment() {

    private var _binding: FragmentUploadHelpBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HelpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadHelpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupViews()
        observeValues()
    }

    private fun setupViewModel() {
        viewModel = HelpViewModel.getHelpViewModel(this)
    }

    private fun setupViews() {
        setupDropDown()
        binding.btnUpload.setOnClickListener {
            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString()
            val contact = binding.etContact.text.toString()
            val resourceType = binding.etResourceType.text.toString()
            val info = binding.etMsg.text.toString()

            viewModel.uploadHelpRequest(name, age, contact, resourceType, info)
        }
    }

    private fun setupDropDown() {
        val resourcesList = resources.getStringArray(R.array.resources_type_list)
        val resourcesAdapter = ArrayAdapter(requireContext(), R.layout.holder_resources_type_dropdown, resourcesList)

        binding.etResourceType.setAdapter(resourcesAdapter)
    }

    private fun observeValues() {
        viewModel.uploadStatus.observe(viewLifecycleOwner) {
            when(it) {
                is Result.EmptySuccess -> {
                    hideLoading()
                    requireContext().showToast("Have faith, stay strong. We really hope you get your help ASAP.")
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
            binding.btnUpload.isEnabled = false
        }
    }

    private fun hideLoading() {
        binding.apply {
            pbLoading.visibility = View.GONE
            binding.btnUpload.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}