package com.example.covid100.ui.news

import androidx.lifecycle.*
import com.example.covid100.data.repositories.NewsRepository
import com.example.covid100.data.response.NewsResponse
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class NewsViewModel(
    private val repo: NewsRepository
) : ViewModel() {

    private val _articlesList = MutableLiveData<Result<NewsResponse>>()
    val articlesList: LiveData<Result<NewsResponse>> get() = _articlesList

    fun fetchNews() = viewModelScope.launch {
        repo.getCovidNews().collect {
            _articlesList.postValue(it)
        }
    }

    init {
        fetchNews()
    }


    companion object {
        private class NewsViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = Injector.getInstance().providesNewsRepository()
                return NewsViewModel(repo) as T
            }
        }

        fun getNewsViewModel(owner: ViewModelStoreOwner) : NewsViewModel {
            return ViewModelProvider(owner, NewsViewModelFactory())[NewsViewModel::class.java]
        }
    }
}