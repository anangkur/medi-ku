package com.anangkur.mediku.feature.view.dashboard.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.databinding.ItemNewsBinding
import com.anangkur.mediku.feature.model.news.ArticleIntent
import com.anangkur.mediku.feature.view.dashboard.main.home.HomeActionListener
import com.anangkur.mediku.util.Const
import com.anangkur.mediku.util.setImageUrl
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val listener: HomeActionListener): BaseAdapter<ItemNewsBinding, ArticleIntent>() {

    override fun bindView(parent: ViewGroup): ItemNewsBinding {
        return ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: ArticleIntent, itemView: ItemNewsBinding, position: Int) {
        itemView.ivItemRegular.setImageUrl(data.urlToImage?:"")
        itemView.tvItemRegular.text = data.title
        itemView.tvNewsSource.text = data.sourceName

        itemView.root.setOnClickListener { listener.onClickNews(data) }

        val dateParsed = try {
            SimpleDateFormat(Const.DATE_FORMAT_NEW_COVID19_2, Locale.US).parse(data.publishedAt)
        }catch (e: Exception){
            try {
                SimpleDateFormat(Const.DEFAULT_DATE_FORMAT, Locale.US).parse(data.publishedAt)
            }catch (e: Exception){
                null
            }
        }

        val dateFormatted = try {
            SimpleDateFormat(Const.DATE_NEWS_SHOWN, Locale.US).format(dateParsed)
        }catch (e: Exception){
            ""
        }
        itemView.tvDateNews.text = dateFormatted
    }
}