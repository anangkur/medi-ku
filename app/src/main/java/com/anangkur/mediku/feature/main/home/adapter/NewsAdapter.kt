package com.anangkur.mediku.feature.main.home.adapter

import android.view.View
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.feature.main.home.HomeActionListener
import com.anangkur.mediku.util.Const
import com.anangkur.mediku.util.formatDate
import com.anangkur.mediku.util.setImageUrl
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val listener: HomeActionListener): BaseAdapter<Article>() {
    override val layout: Int
        get() = R.layout.item_news

    override fun bind(data: Article, itemView: View, position: Int) {
        itemView.iv_item_regular.setImageUrl(data.urlToImage?:"")
        itemView.tv_item_regular.text = data.title
        itemView.setOnClickListener { listener.onClickNews(data) }
        val date = data.publishedAt?:"1990-01-01T00:00:00Z"
        itemView.tv_date_news.text = date.formatDate()
    }
}