package com.anangkur.mediku.feature.mapper

import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.feature.model.ArticleIntent

class ArticleMapper: IntentMapper<ArticleIntent, Article> {

    companion object{
        private var INSTANCE: ArticleMapper? = null
        fun getInstance() = INSTANCE ?: ArticleMapper()
    }

    override fun mapToIntent(data: Article): ArticleIntent {
        return ArticleIntent(
            id = data.id,
            title = data.title,
            author = data.author,
            content = data.content,
            description = data.description,
            publishedAt = data.publishedAt,
            url = data.url,
            urlToImage = data.urlToImage,
            sourceId = data.sourceId,
            sourceName = data.sourceName
        )
    }

    override fun mapFromIntent(data: ArticleIntent): Article {
        return Article(
            id = data.id,
            title = data.title,
            author = data.author,
            content = data.content,
            description = data.description,
            publishedAt = data.publishedAt,
            url = data.url,
            urlToImage = data.urlToImage,
            sourceId = data.sourceId,
            sourceName = data.sourceName
        )
    }
}