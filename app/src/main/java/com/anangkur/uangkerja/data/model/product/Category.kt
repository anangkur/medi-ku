package com.anangkur.uangkerja.data.model.product

import com.google.gson.annotations.SerializedName

data class Category(
    
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("slug")
    val slug: String = "",
    
    @SerializedName("created_at")
    val createdAt: String = "",
    
    @SerializedName("updated_at")
    val updatedAt: String = ""
)