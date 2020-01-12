package com.anangkur.mediku.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseEndlessScrollAdapter<DATA>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @get:LayoutRes
    abstract val layoutItem: Int

    @get:LayoutRes
    abstract val layoutProgress: Int

    private val listItem: ArrayList<DATA> = ArrayList()
    private var showProgress = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == layoutItem){
            BaseEndlessScrollViewHolder(LayoutInflater.from(parent.context).inflate(layoutItem, parent, false))
        }else{
            ProgressViewHolder(LayoutInflater.from(parent.context).inflate(layoutProgress, parent, false))
        }
    }
    override fun getItemCount(): Int {
        return listItem.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseEndlessScrollAdapter<*>.BaseEndlessScrollViewHolder){
            holder.bind(position)
        }else if (holder is BaseEndlessScrollAdapter<*>.ProgressViewHolder){
            holder.bind(showProgress)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == listItem.size){
            layoutProgress
        }else{
            layoutItem
        }
    }

    fun setRecyclerData(data: List<DATA>, positionStart: Int, differentCount: Int){
        if (positionStart == 0){
            this.listItem.clear()
            this.listItem.addAll(data)
            notifyDataSetChanged()
        }else{
            this.listItem.addAll(data)
            notifyItemRangeChanged(positionStart, differentCount)
        }
    }

    fun showProgress(show: Boolean, positionStart: Int, differentCount: Int){
        this.showProgress = show
        if (positionStart == 0){
            notifyDataSetChanged()
        }else{
            notifyItemRangeChanged(positionStart, differentCount)
        }
    }

    abstract fun bindItem(data: DATA, itemView: View, position: Int)
    abstract fun bindProgress(showProgress: Boolean, itemView: View)

    inner class BaseEndlessScrollViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(position: Int){
            bindItem(listItem[position], itemView, position)
        }
    }

    inner class ProgressViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(showProgress: Boolean){
            bindProgress(showProgress, itemView)
        }
    }
}