package com.example.covid100.data.response

import com.google.gson.annotations.SerializedName

data class CovidStatResponse(
    @SerializedName("total_values")
    val totalValues: CovidStatSingleResponse? = null,
    @SerializedName("state_wise")
    val stateWise: StateWiseResponse? = null
)
