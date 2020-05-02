package com.anangkur.mediku.feature.about.resource

import android.view.View
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.about.Resource
import com.anangkur.mediku.feature.about.AboutActionListener
import kotlinx.android.synthetic.main.item_resource_child.view.*

class ResourceChildAdapter(private val listener: AboutActionListener): BaseAdapter<Resource.ResourceChild>(){

    override val layout: Int
        get() = R.layout.item_resource_child

    override fun bind(data: Resource.ResourceChild, itemView: View, position: Int) {
        itemView.iv_res_child.setImageResource(data.image)
        itemView.tv_res_child.text = data.name
        itemView.setOnClickListener { listener.onClickResourceChild(data) }
    }

}