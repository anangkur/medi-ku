package com.anangkur.mediku.feature.model

data class ResourceIntent(
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