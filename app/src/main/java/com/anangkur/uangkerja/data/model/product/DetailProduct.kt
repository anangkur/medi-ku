package com.anangkur.uangkerja.data.model.product

import com.google.gson.annotations.SerializedName

data class DetailProduct(
    
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("name")
    val name: String = "",
    
    @SerializedName("slug")
    val slug: String = "",
    
    @SerializedName("details")
    val details: String = "",
    
    @SerializedName("price")
    val price: Int = 0,
    
    @SerializedName("description")
    val description: String = "",
    
    @SerializedName("featured")
    val featured: Int = 0,
    
    @SerializedName("quantity")
    val quantity: Int = 0,
    
    @SerializedName("image")
    val image: String = "",
    
    @SerializedName("images")
    val images: String = "",
    
    @SerializedName("created_at")
    val createdAt: String = "",
    
    @SerializedName("updated_at")
    val updatedAt: String = "",
    
    @SerializedName("product_name")
    val productName: String = "",
    
    @SerializedName("category_name")
    val categoryName: String = ""
)