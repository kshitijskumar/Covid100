package com.example.covid100.data.repositories

import com.example.covid100.data.api.CovidApiService
import com.example.covid100.utils.Injector

class CovidRepository(
    private val api: CovidApiService = Injector.getInstance().providesCovidApiService()
) : ApiNetworkHelper() {

    suspend fun getCovidStats() = safeApiCall {
        api.getCovidStats()
    }
}