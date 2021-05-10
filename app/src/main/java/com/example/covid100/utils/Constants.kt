package com.example.covid100.utils

object Constants {

    //firebase firestore collection name
    const val RESOURCE_COLLECTION = "resources"
    const val HELP_NEEDED_COLLECTION = "help"

    //date time format for upload date
    const val DATE_TIME_FORMAT = "dd/MM/yyyy 'at' HH:mm:ss"

    //Resource type constants
    const val NULL_RESOURCE_TYPE = -1
    const val HOSPITAL_CODE = 0
    const val OXYGEN_CODE = 1
    const val ICU_CODE = 2
    const val VENTI_CODE = 3
    const val MEDICINE_CODE = 4
    const val PLASMA_CODE = 5

    //News api base url
    const val NEWS_API_KEY = "2b49ce3e318e4547aedeb8c6b3aca021"
    const val NEWS_BASE_URL = "https://newsapi.org/v2/"
}