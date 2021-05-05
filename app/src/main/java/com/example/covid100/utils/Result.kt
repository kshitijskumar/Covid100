package com.example.covid100.utils

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val errorMsg: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
    object EmptySuccess : Result<Nothing>()
}