package com.example.covid100.data.repositories

import android.util.Log
import com.example.covid100.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class ApiNetworkHelper {

    suspend fun <T>safeApiCall(call: suspend () -> Response<T>) = flow<Result<T>> {
        emit(Result.Loading)
        val callResponse = call.invoke()

        if(callResponse.isSuccessful) {
            val body = callResponse.body()
            if(body != null) {
                emit(Result.Success(body))
            }else {
                emit(Result.EmptySuccess)
            }
        }else {
            when(callResponse.code()){
                401 -> emit(Result.Error("Sorry you're not authorized to request news."))
                429 -> emit(Result.Error("You've exceeded the limit of the request. Please try again after some time."))
                500 -> emit(Result.Error("Something went wrong on the server."))
                else -> emit(Result.Error("An unknown error occurred."))
            }
        }
    }.catch { e ->
        Log.d("NetworkHelper", "Error is: ${e.localizedMessage}")
        emit(Result.Error("Something went wrong. Error: ${e.localizedMessage}"))
    }.flowOn(Dispatchers.IO)
}