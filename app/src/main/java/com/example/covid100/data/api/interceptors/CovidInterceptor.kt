package com.example.covid100.data.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class CovidInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .header("x-rapidapi-key", "c5b2df5bdfmsh5673935206eb625p1b762bjsncc5aa3786a52")
            .header("x-rapidapi-host", "corona-virus-world-and-india-data.p.rapidapi.com")
            .build()

        return chain.proceed(newRequest)
    }
}