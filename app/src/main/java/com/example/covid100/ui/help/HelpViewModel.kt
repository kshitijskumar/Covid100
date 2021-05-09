package com.example.covid100.ui.help

import androidx.lifecycle.*
import com.example.covid100.data.model.HelpBody
import com.example.covid100.data.repositories.HelpRepository
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class HelpViewModel(
    private val repo: HelpRepository,
    private val autoFetch: Boolean = false
) : ViewModel() {


    private val _uploadStatus = MutableLiveData<Result<Nothing>>()
    val uploadStatus: LiveData<Result<Nothing>> get() = _uploadStatus

    private val _allHelpRequests = MutableLiveData<Result<List<HelpBody>>>()
    val allHelpRequests : LiveData<Result<List<HelpBody>>> get() = _allHelpRequests

    fun getAllHelpRequests() = viewModelScope.launch {
        repo.getAllHelpNeeded().collect {
            _allHelpRequests.postValue(it)
        }
    }

    companion object {
        private class HelpViewModelFactory(private val autoFetch: Boolean) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = Injector.getInstance().providesHelpRepository()
                return HelpViewModel(repo, autoFetch) as T
            }
        }

        fun getHelpViewModel(owner: ViewModelStoreOwner, autoFetch: Boolean = false) : HelpViewModel {
            return ViewModelProvider(owner, HelpViewModelFactory(autoFetch))[HelpViewModel::class.java]
        }
    }
}