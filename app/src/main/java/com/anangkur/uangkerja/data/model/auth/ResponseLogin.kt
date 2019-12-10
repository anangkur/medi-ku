package com.anangkur.uangkerja.data.model.auth

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    
    @SerializedName("token")
    val token: String = "",
    
    @SerializedName("token_type")
    val tokenType: String = "",
    
    @SerializedName("expires_in")
    val expiresIn: Int = 0
)