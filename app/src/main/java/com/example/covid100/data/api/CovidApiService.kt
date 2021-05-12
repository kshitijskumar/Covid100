package com.example.covid100.data.api

import com.example.covid100.data.api.interceptors.CovidInterceptor
import com.example.covid100.data.response.CovidStatResponse
import com.example.covid100.utils.Constants.COVID_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface CovidApiService {

    @GET("api_india")
    suspend fun getCovidStats() : Response<CovidStatResponse>

    companion object {
        private val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(CovidInterceptor())
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(COVID_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun getCovidApiService() : CovidApiService {
            return retrofit.create(CovidApiService::class.java)
        }
    }
}