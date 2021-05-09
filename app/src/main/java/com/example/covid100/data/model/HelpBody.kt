package com.example.covid100.data.model

import com.example.covid100.utils.Constants.DATE_TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*

data class HelpBody(
    val name: String = "Patient",
    val resourceType: Int? = -1,
    val age: Int? = null,
    val contact: String? = null,
    val date: Long? = null,
    val info: String? = null,
    val id: String? = null
) {
    val dateToDisplay: String by lazy {
        val format = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        format.format(date)
    }
}