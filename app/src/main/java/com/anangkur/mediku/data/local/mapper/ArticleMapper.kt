package com.anangkur.mediku.data.local.mapper

import com.anangkur.mediku.data.local.model.ArticleLocalModel
import com.anangkur.mediku.data.model.news.Article

class ArticleMapper: LocalMapper<ArticleLocalModel, Article> {
    override fun mapToLocal(data: Article): ArticleLocalModel {
        return ArticleLocalModel(
            id = data.id,
            title = data.title,
            author = data.author,
            category = data.category,
            content = data.content,
            description = data.description,
            publishedAt = data.publishedAt,
            url = data.url,
            urlToImage = data.urlToImage,
            country = data.country,
            sourceId = data.sourceId,
            sourceName = data.sourceName
        )
    }

    override fun mapFromLocal(data: ArticleLocalModel): Article {
        return Article(
            id = data.id,
            title = data.title,
            author = data.author,
            category = data.category,
            content = data.content,
            description = data.description,
            publishedAt = data.publishedAt,
            url = data.url,
            urlToImage = data.urlToImage,
            country = data.country,
            sourceId = data.sourceId,
            sourceName = data.sourceName
        )
    }
}