package com.anangkur.mediku.data.model.news

data class Article(
    var id: String = "",
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = "",
    var category: String? = "",
    var country: String? = "",
    val sourceName: String? = "",
    val sourceId: String? = ""
)