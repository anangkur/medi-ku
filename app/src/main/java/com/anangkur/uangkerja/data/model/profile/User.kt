package com.anangkur.uangkerja.data.model.profile

import com.google.gson.annotations.SerializedName

data class User(
    
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("role_id")
    val roleId: Int = 0,
    
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("email")
    val email: String = "",
    
    @SerializedName("avatar")
    val avatar: String = "",
    
    @SerializedName("remember_token")
    val rememberToken: Any = Any(),
    
    @SerializedName("settings")
    val settings: Any = Any(),
    
    @SerializedName("created_at")
    val createdAt: String = "",
    
    @SerializedName("updated_at")
    val updatedAt: String = ""
)