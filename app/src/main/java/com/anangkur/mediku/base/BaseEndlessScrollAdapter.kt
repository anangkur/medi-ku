package com.anangkur.mediku.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseEndlessScrollAdapter<ITEM: ViewBinding, LOADING: ViewBinding, DATA>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract val layoutItem: ITEM

    abstract val layoutProgress: LOADING

    private val listItem: ArrayList<DATA> = ArrayList()
    private var showProgress = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 1){
            BaseEndlessScrollViewHolder(layoutItem)
        }else{
            ProgressViewHolder(layoutProgress)
        }
    }
    override fun getItemCount(): Int {
        return listItem.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseEndlessScrollAdapter<*, *, *>.BaseEndlessScrollViewHolder){
            holder.bind(position)
        }else if (holder is BaseEndlessScrollAdapter<*, *, *>.ProgressViewHolder){
            holder.bind(showProgress)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == listItem.size){
            0
        }else{
            1
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

    abstract fun bindItem(data: DATA, itemView: ITEM, position: Int)
    abstract fun bindProgress(showProgress: Boolean, itemView: LOADING)

    inner class BaseEndlessScrollViewHolder(private val viewBinding: ITEM): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(position: Int){
            bindItem(listItem[position], viewBinding, position)
        }
    }

    inner class ProgressViewHolder(private val viewBinding: LOADING): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(showProgress: Boolean){
            bindProgress(showProgress, viewBinding)
        }
    }
}