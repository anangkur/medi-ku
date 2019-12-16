package com.anangkur.uangkerja.feature.listProduct

import android.view.View
import com.anangkur.uangkerja.BuildConfig
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseEndlessScrollAdapter
import com.anangkur.uangkerja.data.model.product.Product
import com.anangkur.uangkerja.util.currencyFormatToRupiah
import com.anangkur.uangkerja.util.setImageUrl
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_progress.view.*

class ListProductAdapter(private val listener: ListProductActionListener): BaseEndlessScrollAdapter<Product>(){

    override val layoutItem: Int
        get() = R.layout.item_product
    override val layoutProgress: Int
        get() = R.layout.item_progress

    override fun bindItem(data: Product, itemView: View, position: Int) {
        itemView.iv_product.setImageUrl("${BuildConfig.baseImageUrl}${data.image}")
        itemView.tv_product.text = data.productName
        itemView.tv_price.text = data.price.toDouble().currencyFormatToRupiah()
        itemView.setOnClickListener { listener.onClickItem(data.id.toString()) }
    }

    override fun bindProgress(showProgress: Boolean, itemView: View) {
        itemView.pb_loadmore.visibility = if (showProgress){
            View.VISIBLE
        }else{
            View.GONE
        }
    }
}