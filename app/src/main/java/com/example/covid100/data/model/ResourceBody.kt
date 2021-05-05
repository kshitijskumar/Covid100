package com.example.covid100.data.model

data class ResourceBody(
    val id: String? = null,
    val name: String = "Helper",
    val contact: String? = null,
    val resourceType: Int? = null,
    val msg: String? = null,
    val date: String? = null,
    val upVotes: Int = 0,
    val downVotes: Int = 0
)
