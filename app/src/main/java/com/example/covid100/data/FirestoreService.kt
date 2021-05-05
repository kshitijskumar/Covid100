package com.example.covid100.data

import android.util.Log
import com.example.covid100.data.model.ResourceBody
import com.example.covid100.utils.Constants.RESOURCE_COLLECTION
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirestoreService(
    private val db: FirebaseFirestore = Injector.getInstance().providesFirestore()
) {

    companion object {
        private const val TAG = "FirestoreService"
    }

    suspend fun uploadResourceInfo(resource: ResourceBody) : Result<Nothing> {
        return try {
            //uploads data with null id
            val ref = db.collection(RESOURCE_COLLECTION).add(resource).await()
            val id = ref.id
            //gets the id of the uploaded reference and then add the id field to corresponding ref data
            db.collection(RESOURCE_COLLECTION).document(id).set(
                    hashMapOf("id" to id),
                    SetOptions.merge()
            )
            Result.EmptySuccess
        }catch (e: Exception) {
            Log.d(TAG, "upload error: ${e.message}")
            Result.Error("Something went wrong.")
        }
    }

    suspend fun getAllResources() : Result<List<ResourceBody>> {
        return try {
            val response = db.collection(RESOURCE_COLLECTION).get().await()
            val resource = response.toObjects(ResourceBody::class.java)
            if(resource.isNullOrEmpty())
                Result.EmptySuccess
            else
                Result.Success(resource)
        }catch (e: Exception) {
            Log.d(TAG, "get resource error: ${e.message}")
            Result.Error("Something went wrong.")
        }
    }



}