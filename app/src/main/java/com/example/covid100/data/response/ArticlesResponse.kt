package com.example.covid100.data.response

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    val author: String = "Unknown author",
    val title: String? = null,
    val description: String? = null,
    @SerializedName("urlToImage")
    val imgUrl: String? = null,
    @SerializedName("url")
    val newsUrl: String? = null,
    val publishedAt: String? = null
) {
    val dateToDisplay: String by lazy {
        publishedAt?.replace("T", " ", true)
            ?.replace("Z", " ", true) ?: ""
    }
}
