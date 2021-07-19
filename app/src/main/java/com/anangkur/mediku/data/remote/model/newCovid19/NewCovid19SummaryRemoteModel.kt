package com.anangkur.mediku.data.remote.model.newCovid19

import com.google.gson.annotations.SerializedName

data class NewCovid19SummaryRemoteModel(

    @SerializedName("Country")
    val country: String = "",

    @SerializedName("NewConfirmed")
    val newConfirmed: Int? = 0,

    @SerializedName("NewDeaths")
    val newDeaths: Int? = 0,

    @SerializedName("NewRecovered")
    val newRecovered: Int? = 0,

    @SerializedName("Slug")
    val slug: String? = "",

    @SerializedName("TotalConfirmed")
    val totalConfirmed: Int? = 0,

    @SerializedName("TotalDeaths")
    val totalDeaths: Int? = 0,

    @SerializedName("TotalRecovered")
    val totalRecovered: Int? = 0,

    var date: String? = ""
)