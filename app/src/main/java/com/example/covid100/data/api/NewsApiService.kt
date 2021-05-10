package com.example.covid100.data.api

import com.example.covid100.data.response.NewsResponse
import com.example.covid100.utils.Constants
import com.example.covid100.utils.Constants.NEWS_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface NewsApiService {

    @GET("everything?q=covid19&apiKey=${Constants.NEWS_API_KEY}")
    suspend fun getCovidNews() : Response<NewsResponse>


    companion object {
        private val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(NEWS_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun getNewsApiService() : NewsApiService {
            return retrofit.create(NewsApiService::class.java)
        }
    }
}