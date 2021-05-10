package com.example.covid100.ui.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covid100.databinding.FragmentNewsBinding
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions.showToast

class NewsFragment : Fragment() {

    private var _binding : FragmentNewsBinding? = null
    private val binding : FragmentNewsBinding get() = _binding!!

    private lateinit var viewModel: NewsViewModel

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupViews()
        observeValues()
    }

    private fun setupViewModel() {
        viewModel = NewsViewModel.getNewsViewModel(this)
    }

    private fun setupViews() {
        newsAdapter = NewsAdapter {
            Log.d("NewsFragment", "News item url is : $it")
            openNewsInBrowser(it)
        }

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchNews()
        }
    }

    private fun observeValues() {
        viewModel.articlesList.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    newsAdapter.submitList(it.data.articles)
                }
                is Result.Error -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    requireContext().showToast(it.errorMsg)
                }
                is Result.Loading -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }
                is Result.EmptySuccess -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    requireContext().showToast("Api call invoked successfully, but no news fetched.")
                }
            }
        }
    }

    private fun openNewsInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}