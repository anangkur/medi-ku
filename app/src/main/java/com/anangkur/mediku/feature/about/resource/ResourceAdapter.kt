package com.anangkur.mediku.feature.about.resource

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseAdapter
import com.anangkur.mediku.data.model.about.Resource
import com.anangkur.mediku.feature.about.AboutActionListener
import com.anangkur.mediku.util.setupRecyclerViewGrid
import kotlinx.android.synthetic.main.item_resource_parent.view.*

class ResourceAdapter(private val listener: AboutActionListener): BaseAdapter<Resource>(){

    override val layout: Int
        get() = R.layout.item_resource_parent

    private lateinit var childAdapter: ResourceChildAdapter

    override fun bind(data: Resource, itemView: View, position: Int) {
        setupChildAdapter(itemView.recycler_resource_child)
        itemView.tv_title_resource.text = data.title
        childAdapter.setRecyclerData(data.child)
        itemView.tv_title_resource.setOnClickListener { listener.onClickResourceParent(data) }
    }

    private fun setupChildAdapter(recyclerView: RecyclerView){
        childAdapter = ResourceChildAdapter(listener)
        recyclerView.apply {
            adapter = childAdapter
            setupRecyclerViewGrid(recyclerView.context, 4)
        }
    }

}