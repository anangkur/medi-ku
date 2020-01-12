package com.anangkur.mediku.data.model.product

import com.google.gson.annotations.SerializedName

data class Product(
    
    @SerializedName("id")
    val id: Int = 0,
    
    @SerializedName("product_name")
    val productName: String = "",
    
    @SerializedName("price")
    val price: Int = 0,
    
    @SerializedName("quantity")
    val quantity: Int = 0,
    
    @SerializedName("image")
    val image: String = "",
    
    @SerializedName("category_id")
    val categoryId: Int = 0,
    
    @SerializedName("category_name")
    val categoryName: String = ""
)