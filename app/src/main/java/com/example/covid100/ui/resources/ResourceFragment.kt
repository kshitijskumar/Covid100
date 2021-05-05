package com.example.covid100.ui.resources

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.covid100.databinding.FragmentResourcesBinding
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result

class ResourceFragment : Fragment() {

    private var _binding : FragmentResourcesBinding? = null
    private val binding : FragmentResourcesBinding get() = _binding!!

    private lateinit var viewModel: ResourceViewModel

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
        observeValues()

        val injector = Injector.getInstance()
        Log.d("Injector", "Injector is $injector")
        Log.d("Injector", "Firestore is ${injector.providesFirestore()}")
        Log.d("Injector", "Firestore is ${injector.providesFirestoreService()}")


    }

    private fun setupViewModel() {
        viewModel = ResourceViewModel.getResourceViewModel(this)
    }

    private fun observeValues() {
        viewModel.resources.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    Log.d("Resources", "List is ${it.data}")
                }
                is Result.Error -> {
                    Log.d("Resources", "Error is ${it.errorMsg}")
                }
                is Result.Loading -> {
                    Log.d("Resources", "Loading")
                }
                is Result.EmptySuccess -> {
                    Log.d("Resources", "Empty success")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}