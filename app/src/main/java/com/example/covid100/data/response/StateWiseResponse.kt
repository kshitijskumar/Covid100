package com.example.covid100.data.response

import com.google.gson.annotations.SerializedName

data class StateWiseResponse(
    @SerializedName("Maharashtra")
    val maharashtra: CovidStatSingleResponse? = null,
    @SerializedName("Uttar Pradesh")
    val up: CovidStatSingleResponse? = null,
    @SerializedName("Delhi")
    val delhi: CovidStatSingleResponse? = null,
    @SerializedName("Rajasthan")
    val rajasthan: CovidStatSingleResponse? = null,
    @SerializedName("West Bengal")
    val westBengal: CovidStatSingleResponse? = null,
    @SerializedName("Kerala")
    val kerala: CovidStatSingleResponse? = null,
    @SerializedName("Punjab")
    val punjab: CovidStatSingleResponse? = null,
    @SerializedName("Uttarakhand")
    val uttarakhand: CovidStatSingleResponse? = null,
    @SerializedName("Goa")
    val goa: CovidStatSingleResponse? = null,
    @SerializedName("Tamil Nadu")
    val tamilNadu: CovidStatSingleResponse? = null,
) {

    val listOfStatesData: List<CovidStatSingleResponse?> by lazy {
        listOf(maharashtra, up, delhi, rajasthan, westBengal, kerala, punjab, uttarakhand, goa, tamilNadu)
    }
}
