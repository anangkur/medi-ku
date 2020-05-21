package com.anangkur.mediku.feature.about.resource

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.about.Resource
import com.anangkur.mediku.databinding.ItemResourceParentBinding
import com.anangkur.mediku.feature.about.AboutActionListener
import com.anangkur.mediku.util.setupRecyclerViewGrid
import kotlinx.android.synthetic.main.item_resource_parent.view.*

class ResourceParentAdapter(private val listener: AboutActionListener): BaseAdapter<ItemResourceParentBinding, Resource>(){

    private lateinit var childAdapter: ResourceChildAdapter

    override fun bindView(parent: ViewGroup): ItemResourceParentBinding {
        return ItemResourceParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(data: Resource, itemView: ItemResourceParentBinding, position: Int) {
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