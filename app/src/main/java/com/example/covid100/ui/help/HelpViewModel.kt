package com.example.covid100.ui.help

import androidx.lifecycle.*
import com.example.covid100.data.model.HelpBody
import com.example.covid100.data.repositories.HelpRepository
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions.mapResourceStringToResourceCode
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@Suppress("UNCHECKED_CAST")
class HelpViewModel(
    private val repo: HelpRepository,
    private val autoFetch: Boolean = false
) : ViewModel() {


    private val _uploadStatus = MutableLiveData<Result<Nothing>>()
    val uploadStatus: LiveData<Result<Nothing>> get() = _uploadStatus

    private val _allHelpRequests = MutableLiveData<Result<List<HelpBody>>>()
    val allHelpRequests : LiveData<Result<List<HelpBody>>> get() = _allHelpRequests

    private val _helpInfo = MutableLiveData<Result<HelpBody>>()
    val helpInfo : LiveData<Result<HelpBody>> get() = _helpInfo

    fun getAllHelpRequests() = viewModelScope.launch {
        repo.getAllHelpNeeded().collect {
            _allHelpRequests.postValue(it)
        }
    }

    fun uploadHelpRequest(
        name: String?,
        age: String?,
        contact: String?,
        resourceType: String?,
        info: String
    ) = viewModelScope.launch {
        _uploadStatus.value = Result.Loading

        if(age.isNullOrEmpty()) {
            _uploadStatus.value = Result.Error("Please enter the age of the patient.")
        }else if (contact.isNullOrEmpty()){
            _uploadStatus.value = Result.Error("Please enter a contact number.")
        }else if(contact.length != 10) {
            _uploadStatus.value = Result.Error("Please enter a valid 10 digit contact number.")
        }else if(resourceType.isNullOrEmpty()) {
            _uploadStatus.value = Result.Error("Please choose a resource type you're looking for.")
        }else {
            val intAge = age.toInt()
            val resourceCode = mapResourceStringToResourceCode(resourceType)
            val helpName = if(name.isNullOrEmpty()) "Someone" else name

            val help = HelpBody(
                helpName,
                resourceCode,
                intAge,
                contact,
                Calendar.getInstance().timeInMillis,
                info
            )

            repo.uploadHelpRequest(help).collect {
                _uploadStatus.postValue(it)
            }

        }
    }

    fun getHelpRequestInfo(id: String) = viewModelScope.launch {
        repo.getHelpRequestInfo(id).collect{
            _helpInfo.postValue(it)
        }
    }

    init {
        if (autoFetch) {
            getAllHelpRequests()
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