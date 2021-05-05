package com.example.covid100.utils

object UtilFunctions {

    fun mapResourceStringToResourceCode(resourceString: String) : Int {
        return when(resourceString) {
            "Hospital" -> 0
            "Oxygen" -> 1
            "ICU" -> 2
            "Ventilator" -> 3
            "Medicines" -> 4
            "Plasma" -> 5
            else -> -1
        }
    }
}