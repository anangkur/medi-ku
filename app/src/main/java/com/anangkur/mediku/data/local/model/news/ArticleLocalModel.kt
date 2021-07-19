package com.anangkur.mediku.data.local.model.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleLocalModel(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String = "",

    @ColumnInfo(name = "author")
    val author: String? = "",

    @ColumnInfo(name = "title")
    val title: String? = "",

    @ColumnInfo(name = "description")
    val description: String? = "",

    @ColumnInfo(name = "url")
    val url: String? = "",

    @ColumnInfo(name = "urlToImage")
    val urlToImage: String? = "",

    @ColumnInfo(name = "publishedAt")
    val publishedAt: String? = "",

    @ColumnInfo(name = "content")
    val content: String? = "",

    @ColumnInfo(name = "category")
    val category: String? = "",

    @ColumnInfo(name = "country")
    val country: String? = "",

    @ColumnInfo(name = "sourceName")
    val sourceName: String? = "",

    @ColumnInfo(name = "sourceId")
    val sourceId: String? = ""

)