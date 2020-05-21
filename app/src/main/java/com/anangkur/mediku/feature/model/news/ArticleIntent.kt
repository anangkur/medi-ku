package com.anangkur.mediku.feature.model.news

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleIntent(
    val id: String = "",
    val author: String? = "",
    val title: String? = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = "",
    val content: String? = "",
    val category: String? = "",
    val country: String? = "",
    val sourceName: String? = "",
    val sourceId: String? = ""
): Parcelable