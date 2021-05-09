package com.example.covid100.ui.resources

import android.util.Log
import androidx.lifecycle.*
import com.example.covid100.data.model.ResourceBody
import com.example.covid100.data.repositories.ResourceRepository
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result
import com.example.covid100.utils.UtilFunctions.mapResourceStringToResourceCode
import kotlinx.coroutines.launch

class ResourceViewModel(
        private val repo : ResourceRepository,
        autoFetch: Boolean = true
) : ViewModel() {

    private val _uploadStatus = MutableLiveData<Result<Nothing>>()
    val uploadStatus: LiveData<Result<Nothing>>
        get() = _uploadStatus

    private val _resources = MutableLiveData<Result<List<ResourceBody>>>()
    val resources: LiveData<Result<List<ResourceBody>>>
        get() = _resources

    private val _resourceInfo = MutableLiveData<Result<ResourceBody>>()
    val resourceInfo : LiveData<Result<ResourceBody>>
        get() = _resourceInfo

    init {
        if(autoFetch) {
            getAllResources()
        }
    }

    fun uploadResource(
            name: String = "Helper",
            contact: String?,
            resourceType: String?,
            msg: String? = null
    ) = viewModelScope.launch {

        _uploadStatus.value = Result.Loading

        if(contact.isNullOrEmpty()) {
            _uploadStatus.value = Result.Error("Please enter a valid contact detail.")
        }else if(contact.length < 10) {
            _uploadStatus.value = Result.Error("Please enter a valid 10 digit phone number.")
        }else if(resourceType.isNullOrEmpty()) {
            _uploadStatus.value = Result.Error("Please enter a valid resource type.")
        }else {
            val resourceCode = mapResourceStringToResourceCode(resourceType)
            _uploadStatus.value = repo.uploadResource(name, contact, resourceCode, msg)
        }
    }

    fun getAllResources() = viewModelScope.launch {
        _resources.value = Result.Loading
        _resources.value = repo.getAllResource()
    }

    fun getSpecificResource(type: Int) : List<ResourceBody> {
        return when(val value = _resources.value) {
            is Result.Success -> {
                value.data.filter {
                    it.resourceType == type
                }
            }
            else -> listOf()
        }
    }

    fun likeDislikeResource(
        id: String,
        upvotes: Int,
        downvotes: Int,
        isLike: Boolean,
        isToggled: Boolean = false
    ) = viewModelScope.launch {
        var updateUpvote = upvotes
        var updateDownvotes = downvotes

        if(isLike) {
            updateUpvote += 1
            if(isToggled) {
                updateDownvotes -= 1
            }
        }else {
            updateDownvotes += 1
            if (isToggled) {
                updateUpvote -= 1
            }
        }

        repo.likeDislikeResource(id, updateUpvote, updateDownvotes)
    }

    fun getResourceInfo(id: String) = viewModelScope.launch {
        _resourceInfo.value = Result.Loading
        _resourceInfo.value = repo.getResourceInfo(id)
    }


    companion object {
        @Suppress("UNCHECKED_CAST")
        private class ResourceVMFactory(private val autoFetch: Boolean) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = Injector.getInstance().providesResourceRepository()
                return ResourceViewModel(repo, autoFetch) as T
            }
        }

        fun getResourceViewModel(owner: ViewModelStoreOwner, autoFetch: Boolean = true) : ResourceViewModel {
            return ViewModelProvider(owner, ResourceVMFactory(autoFetch))[ResourceViewModel::class.java]
        }
    }
}