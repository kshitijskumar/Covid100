package com.example.covid100.data.response

data class NewsResponse(
    val status: String? = null,
    val totalResults: Int = 0,
    val articles: List<ArticlesResponse> = listOf()
)
