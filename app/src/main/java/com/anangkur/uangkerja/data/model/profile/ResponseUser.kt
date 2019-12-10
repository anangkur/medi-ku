package com.anangkur.uangkerja.data.model.profile

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("user")
    val user: User = User()
)