package com.example.covid100.utils

import android.content.Context
import android.widget.Toast
import com.example.covid100.utils.Constants.HOSPITAL_CODE
import com.example.covid100.utils.Constants.ICU_CODE
import com.example.covid100.utils.Constants.MEDICINE_CODE
import com.example.covid100.utils.Constants.OXYGEN_CODE
import com.example.covid100.utils.Constants.PLASMA_CODE
import com.example.covid100.utils.Constants.VENTI_CODE

object UtilFunctions {

    fun mapResourceStringToResourceCode(resourceString: String) : Int {
        return when(resourceString) {
            "Hospital" -> HOSPITAL_CODE
            "Oxygen" -> OXYGEN_CODE
            "ICU" -> ICU_CODE
            "Ventilator" -> VENTI_CODE
            "Medicines" -> MEDICINE_CODE
            "Plasma" -> PLASMA_CODE
            else -> -1
        }
    }

    fun mapResourceCodeToResourceString(resourceCode: Int) : String {
        return when(resourceCode) {
            HOSPITAL_CODE -> "Hospital"
            OXYGEN_CODE -> "Oxygen"
            ICU_CODE -> "ICU"
            VENTI_CODE -> "Ventilator"
            MEDICINE_CODE -> "Medicines"
            PLASMA_CODE -> "Plasma"
            else -> "Not specified"
        }
    }

    fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, msg, duration).show()
    }

    fun returnShareText(name: String?, contact: String?, msg: String?, resourceType: Int) : String {
        return "Please contact $name, $contact for ${mapResourceCodeToResourceString(resourceType)}." +
                " Information: $msg - Covid100"
    }
}