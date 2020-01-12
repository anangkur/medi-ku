package com.anangkur.mediku.data.model.auth

import com.google.gson.annotations.SerializedName

data class Register(
    
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("email")
    val email: String = "",
    
    @SerializedName("role_id")
    val roleId: Int = 0,
    
    @SerializedName("updated_at")
    val updatedAt: String = "",
    
    @SerializedName("created_at")
    val createdAt: String = "",
    
    @SerializedName("id")
    val id: Int = 0
)