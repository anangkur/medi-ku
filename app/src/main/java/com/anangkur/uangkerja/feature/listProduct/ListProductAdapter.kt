package com.anangkur.uangkerja.feature.listProduct

import android.view.View
import com.anangkur.uangkerja.BuildConfig
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseAdapter
import com.anangkur.uangkerja.data.model.product.Product
import com.anangkur.uangkerja.util.currencyFormatToRupiah
import com.anangkur.uangkerja.util.setImageUrl
import kotlinx.android.synthetic.main.item_product.view.*

class ListProductAdapter: BaseAdapter<Product>(){
    override val layout: Int
        get() = R.layout.item_product

    override fun bind(data: Product, itemView: View, position: Int) {
        itemView.iv_product.setImageUrl("${BuildConfig.baseImageUrl}${data.image}")
        itemView.tv_product.text = data.productName
        itemView.tv_price.text = data.price.toDouble().currencyFormatToRupiah()
    }
}