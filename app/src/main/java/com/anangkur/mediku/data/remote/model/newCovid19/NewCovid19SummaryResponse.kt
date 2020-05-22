package com.anangkur.mediku.data.remote.model.newCovid19

import com.google.gson.annotations.SerializedName

data class NewCovid19SummaryResponse(

    @SerializedName("Countries")
    val countries: List<NewCovid19SummaryRemoteModel>? = listOf(),

    @SerializedName("Date")
    var date: String? = ""
)