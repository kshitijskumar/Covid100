package com.example.covid100.ui.help

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.covid100.data.FirestoreService
import com.example.covid100.databinding.FragmentHelpBinding
import com.example.covid100.utils.Injector

class HelpFragment : Fragment() {

    private var _binding : FragmentHelpBinding? = null
    private val binding : FragmentHelpBinding get() = _binding!!

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
        val injector = Injector.getInstance()
        Log.d("Injector", "Injector is $injector")
        Log.d("Injector", "Firestore is ${injector.providesFirestore()}")
        Log.d("Injector", "Firestore is ${injector.providesFirestoreService()}")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}