package com.example.covid100.ui.resourcedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covid100.databinding.BottomSheetFragmentResourceDetailBinding
import com.example.covid100.ui.resources.ResourceViewModel
import com.example.covid100.utils.Constants.NULL_RESOURCE_TYPE
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions.mapResourceCodeToResourceString
import com.example.covid100.utils.UtilFunctions.returnShareText
import com.example.covid100.utils.UtilFunctions.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ResourceDetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding : BottomSheetFragmentResourceDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ResourceViewModel

    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString("id")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFragmentResourceDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupViews()
        observeValues()
    }

    private fun setupViewModel() {
        viewModel = ResourceViewModel.getResourceViewModel(this, false)
    }

    private fun setupViews() {
        id?.let {
            viewModel.getResourceInfo(it)
        }
    }

    private fun observeValues() {
        viewModel.resourceInfo.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Error -> requireContext().showToast(it.errorMsg)
                is Result.Success -> {
                    val info = it.data
                    binding.apply {
                        tvDate.text = info.date
                        tvName.text = info.name
                        tvContact.text = info.contact
                        tvMsg.text = info.msg
                        tvResourceType.text = mapResourceCodeToResourceString(info.resourceType ?: -1)
                    }
                    binding.btnCall.setOnClickListener {
                        info.contact?.let { contact ->
                            callResource(contact)
                        }
                    }

                    binding.btnShare.setOnClickListener {
                        shareResource(info.name, info.contact, info.msg, info.resourceType ?: NULL_RESOURCE_TYPE)
                    }
                }
            }
        }
    }

    private fun callResource(contact: String) {
        val callIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel: $contact")
        }
        startActivity(callIntent)
    }

    private fun shareResource(name: String?, contact: String?, msg: String?, resourceType: Int) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, returnShareText(name, contact, msg, resourceType))
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}