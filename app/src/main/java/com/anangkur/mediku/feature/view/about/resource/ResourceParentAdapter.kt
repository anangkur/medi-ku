package com.anangkur.mediku.feature.view.about.resource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.feature.model.ResourceIntent
import com.anangkur.mediku.databinding.ItemResourceParentBinding
import com.anangkur.mediku.feature.view.about.AboutActionListener
import com.anangkur.mediku.util.setupRecyclerViewGrid

class ResourceParentAdapter(private val listener: AboutActionListener): BaseAdapter<ItemResourceParentBinding, ResourceIntent>(){

    private lateinit var childAdapter: ResourceChildAdapter

    override fun bindView(parent: ViewGroup): ItemResourceParentBinding {
        return ItemResourceParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: ResourceIntent, itemView: ItemResourceParentBinding, position: Int) {
        setupChildAdapter(itemView.recyclerResourceChild)
        itemView.tvTitleResource.text = "- ${data.title}"
        childAdapter.setRecyclerData(data.child)
        itemView.tvTitleResource.setOnClickListener { listener.onClickResourceParent(data) }
    }

    private fun setupChildAdapter(recyclerView: RecyclerView){
        childAdapter = ResourceChildAdapter(listener)
        recyclerView.apply {
            adapter = childAdapter
            setupRecyclerViewGrid(recyclerView.context, 6)
        }
    }

}