package com.anangkur.mediku.data.model.newCovid19

data class NewCovid19Country(
    var id: String = "",
    val date: String? = "",
    val country: String? = "",
    val lat: Double? = 0.0,
    val lon: Double? = 0.0,
    val province: String? = "",
    val cases: Int? = 0,
    val status: String? = ""
)