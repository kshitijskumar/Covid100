package com.example.covid100.data.repositories

import com.example.covid100.data.FirestoreService
import com.example.covid100.data.model.ResourceBody
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class ResourceRepository(
    private val firestoreService: FirestoreService = Injector.getInstance().providesFirestoreService()
) {

    suspend fun uploadResource(
            name: String = "Helper",
            contact: String? = null,
            resourceType: Int? = null,
            msg: String? = null
    ) : Result<Nothing> {
        return withContext(Dispatchers.IO) {
            val date = Calendar.getInstance().timeInMillis
            val resource = ResourceBody(name= name, contact = contact, resourceType = resourceType, msg = msg, date = date)
            firestoreService.uploadResourceInfo(resource)
        }
    }

    suspend fun getAllResource() : Result<List<ResourceBody>> {
        return withContext(Dispatchers.IO) {
            firestoreService.getAllResources()
        }
    }

    suspend fun likeDislikeResource(id: String, upvotes: Int, downvotes: Int) {
        withContext(Dispatchers.IO) {
            firestoreService.likeDislikeResource(id, upvotes, downvotes)
        }
    }

    suspend fun getResourceInfo(id: String) : Result<ResourceBody> {
        return withContext(Dispatchers.IO) {
            firestoreService.getResourceInfo(id)
        }
    }
}