package com.example.covid100.utils

import android.content.Context
import android.widget.Toast

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

    fun mapResourceCodeToResourceString(resourceCode: Int) : String {
        return when(resourceCode) {
            0 -> "Hospital"
            1 -> "Oxygen"
            2 -> "ICU"
            3 -> "Ventilator"
            4 -> "Medicines"
            5 -> "Plasma"
            else -> "Not specified"
        }
    }

    fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, msg, duration).show()
    }
}