package com.anangkur.mediku.data.remote.mapper

import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.data.remote.model.news.ArticleRemoteModel
import com.anangkur.mediku.data.remote.model.news.Source

class ArticleMapper: RemoteMapper<ArticleRemoteModel, Article> {

    companion object{
        private var INSTANCE: ArticleMapper? = null
        fun getInstance() = INSTANCE ?: ArticleMapper()
    }

    override fun mapFromRemote(data: ArticleRemoteModel): Article {
        return Article(
            title = data.title,
            author = data.author,
            content = data.content,
            description = data.description,
            publishedAt = data.publishedAt,
            url = data.url,
            urlToImage = data.urlToImage,
            sourceId = data.source?.id,
            sourceName = data.source?.name
        )
    }

    override fun mapToRemote(data: Article): ArticleRemoteModel {
        return ArticleRemoteModel(
            title = data.title,
            author = data.author,
            content = data.content,
            description = data.description,
            publishedAt = data.publishedAt,
            url = data.url,
            urlToImage = data.urlToImage,
            source = Source(data.sourceId?:"", data.sourceName?:"")
        )
    }
}