package com.example.covid100.data.repositories

import com.example.covid100.data.api.NewsApiService
import com.example.covid100.utils.Injector

class NewsRepository(
    private val newsApiService: NewsApiService = Injector.getInstance().providesNewsApiService()
) : ApiNetworkHelper() {

    suspend fun getCovidNews() = safeApiCall {
        newsApiService.getCovidNews()
    }
}