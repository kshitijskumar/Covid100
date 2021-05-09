package com.example.covid100.data.model

import com.example.covid100.utils.Constants
import java.text.SimpleDateFormat
import java.util.*

data class ResourceBody(
    val id: String? = null,
    val name: String = "Helper",
    val contact: String? = null,
    val resourceType: Int? = null,
    val msg: String? = null,
    val date: Long? = null,
    val upVotes: Int = 0,
    val downVotes: Int = 0
) {
    val dateToDisplay : String by lazy {
        val format = SimpleDateFormat(Constants.DATE_TIME_FORMAT, Locale.getDefault())
        format.format(date)
    }
}
