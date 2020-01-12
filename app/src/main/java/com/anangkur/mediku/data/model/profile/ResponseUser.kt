package com.anangkur.mediku.data.model.profile

import com.google.gson.annotations.SerializedName

data class ResponseUser(
    @SerializedName("user")
    val user: User = User()
)