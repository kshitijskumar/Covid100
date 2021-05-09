package com.example.covid100.ui.help

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.covid100.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private var _binding : FragmentHelpBinding? = null
    private val binding : FragmentHelpBinding get() = _binding!!

    private lateinit var viewModel: HelpViewModel

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
    }

    private fun setupViewModel() {
        viewModel = HelpViewModel.getHelpViewModel(this, true)
    }

    private fun setupViews() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}