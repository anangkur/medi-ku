package com.anangkur.mediku.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<DATA>: RecyclerView.Adapter<BaseAdapter<DATA>.BaseViewHolder>() {

    @get:LayoutRes
    abstract val layout: Int

    private val listItem: ArrayList<DATA> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }
    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(listItem[position], position)
    }

    fun setRecyclerData(data: List<DATA>){
        listItem.clear()
        listItem.addAll(data)
        notifyDataSetChanged()
    }

    abstract fun bind(data: DATA, itemView: View, position: Int)

    inner class BaseViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(data: DATA, position: Int){
            bind(data, itemView, position)
        }
    }
}