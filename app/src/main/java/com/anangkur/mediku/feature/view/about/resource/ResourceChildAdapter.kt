package com.anangkur.mediku.feature.view.about.resource

import android.view.LayoutInflater
import android.view.ViewGroup
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.feature.model.ResourceIntent
import com.anangkur.mediku.databinding.ItemResourceChildBinding
import com.anangkur.mediku.feature.view.about.AboutActionListener

class ResourceChildAdapter(private val listener: AboutActionListener): BaseAdapter<ItemResourceChildBinding, ResourceIntent.ResourceChild>(){

    override fun bindView(parent: ViewGroup): ItemResourceChildBinding {
        return ItemResourceChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: ResourceIntent.ResourceChild, itemView: ItemResourceChildBinding, position: Int) {
        itemView.ivResChild.setImageResource(data.image)
        itemView.tvResChild.text = data.name
        itemView.root.setOnClickListener { listener.onClickResourceChild(data) }
    }

}