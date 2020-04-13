package com.anangkur.mediku.data.model.newCovid19

import com.google.gson.annotations.SerializedName

data class NewCovid19SummaryResponse(

    @SerializedName("Countries")
    val countries: List<NewCovid19Summary>? = listOf(),

    @SerializedName("Date")
    var date: String? = ""
)