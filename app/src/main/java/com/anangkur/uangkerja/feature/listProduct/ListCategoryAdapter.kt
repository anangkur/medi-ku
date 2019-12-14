package com.anangkur.uangkerja.feature.listProduct

import android.view.View
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseAdapter
import com.anangkur.uangkerja.data.model.product.Category
import kotlinx.android.synthetic.main.item_category.view.*

class ListCategoryAdapter(private val listener: ListProductActionListener): BaseAdapter<Category>(){

    override val layout: Int
        get() = R.layout.item_category

    override fun bind(data: Category, itemView: View, position: Int) {
        itemView.tv_category.text = data.name
        itemView.setOnClickListener {
            listener.onClickCategory(data.id, data.name)
        }
    }
}