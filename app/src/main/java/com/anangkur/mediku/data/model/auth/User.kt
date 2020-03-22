package com.anangkur.mediku.data.model.auth

data class User(
    var email: String = "",
    var name: String = "",
    var height: Int = 0,
    var weight: Int = 0,
    var photo: String = "",
    var providerName: String = ""
)