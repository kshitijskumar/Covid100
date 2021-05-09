package com.example.covid100.data

import android.util.Log
import com.example.covid100.data.model.HelpBody
import com.example.covid100.data.model.ResourceBody
import com.example.covid100.utils.Constants.HELP_NEEDED_COLLECTION
import com.example.covid100.utils.Constants.RESOURCE_COLLECTION
import com.example.covid100.utils.Injector
import com.example.covid100.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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
            ).await()
            Result.EmptySuccess
        }catch (e: Exception) {
            Log.d(TAG, "upload error: ${e.message}")
            Result.Error("Something went wrong.")
        }
    }

    suspend fun getAllResources() : Result<List<ResourceBody>> {
        return try {
            val response = db.collection(RESOURCE_COLLECTION)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .await()
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

    suspend fun likeDislikeResource(id: String, upvotes: Int, downvotes: Int) {
        try {
            val map = hashMapOf<String, Int>(
                "upVotes" to upvotes,
                "downVotes" to downvotes
            )
            db.collection(RESOURCE_COLLECTION).document(id).set(
                map,
                SetOptions.merge()
            ).await()
        }catch (e: Exception) {
            Log.d(TAG, "like dislike error: ${e.message}")
        }
    }

    suspend fun getResourceInfo(id: String) : Result<ResourceBody> {
        return try{
            val info = db.collection(RESOURCE_COLLECTION)
                .document(id)
                .get()
                .await()
                .toObject(ResourceBody::class.java)
            if (info == null) {
                Result.Error("No information found.")
            }else {
                Result.Success(info)
            }
        }catch (e: Exception) {
            Log.d(TAG, "get resource info error: ${e.message}")
            Result.Error("Something went wrong.")
        }
    }

    suspend fun getAllHelpNeeded() = flow<Result<List<HelpBody>>> {
        emit(Result.Loading)

        val info = db.collection(HELP_NEEDED_COLLECTION)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .await().toObjects(HelpBody::class.java)

        if(info.isNullOrEmpty()) {
            emit(Result.EmptySuccess)
        }else {
            emit(Result.Success(info))
        }
    }
        .catch { e ->
            Log.d(TAG, "get all help needed error: ${e.message}")
            emit(Result.Error("Something went wrong while fetching. ${e.localizedMessage}"))
        }
        .flowOn(Dispatchers.IO)


    suspend fun uploadHelpRequest(help: HelpBody) = flow {
        emit(Result.Loading)

        val ref = db.collection(HELP_NEEDED_COLLECTION)
            .add(help).await()

        val id = ref.id

        db.collection(HELP_NEEDED_COLLECTION)
            .document(id)
            .set(
                hashMapOf(
                    "id" to id
                ),
                SetOptions.merge()
            ).await()

        emit(Result.EmptySuccess)
    }.catch { e ->
        Log.d(TAG, "upload help error: ${e.localizedMessage}")
        emit(Result.Error("Error while uploading the request. ${e.localizedMessage}"))
    }.flowOn(Dispatchers.IO)
}