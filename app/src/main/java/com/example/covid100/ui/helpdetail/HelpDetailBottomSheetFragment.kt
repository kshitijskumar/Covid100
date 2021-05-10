package com.example.covid100.ui.helpdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.covid100.databinding.BottomSheetFragmentHelpDetailBinding
import com.example.covid100.ui.help.HelpViewModel
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions
import com.example.covid100.utils.UtilFunctions.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HelpDetailBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding : BottomSheetFragmentHelpDetailBinding? = null
    private val binding get() = _binding!!

    private var id: String? = null

    private lateinit var viewModel: HelpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = arguments?.getString("id")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFragmentHelpDetailBinding.inflate(inflater)
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
        id?.let {
            viewModel.getHelpRequestInfo(it)
        }
    }

    private fun observeValues() {
        viewModel.helpInfo.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    val info = it.data
                    binding.apply {
                        tvDate.text = info.dateToDisplay
                        tvName.text = info.name
                        tvContact.text = info.contact
                        tvMsg.text = info.info
                        tvResourceType.text = UtilFunctions.mapResourceCodeToResourceString(info.resourceType ?: -1)
                    }

                    binding.btnCall.setOnClickListener {
                        info.contact?.let { contact ->
                            callResource(contact)
                        }
                    }
                }
                is Result.Error -> {
                    requireContext().showToast(it.errorMsg)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}