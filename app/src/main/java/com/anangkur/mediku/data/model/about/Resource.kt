package com.anangkur.mediku.data.model.about

data class Resource(
    val title: String,
    val child: List<ResourceChild>,
    val link: String
){
    data class ResourceChild(
        val image: Int,
        val name: String,
        val link: String
    )
}