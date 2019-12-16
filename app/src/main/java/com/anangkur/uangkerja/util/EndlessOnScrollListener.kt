package com.anangkur.uangkerja.util

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class EndlessOnScrollListener(var mLoading: Boolean): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy > 0){
            val visibleItemCount = recyclerView.layoutManager!!.childCount
            val totalItemCount = recyclerView.layoutManager!!.itemCount
            val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
             Log.d("EndlessOnScrollListener", "visibleItemCount: $visibleItemCount | totalItemCount: $totalItemCount | firstVisibleItem: $firstVisibleItem | mLoading: $mLoading")
            if (mLoading && (visibleItemCount + firstVisibleItem) >= totalItemCount){
                mLoading = false
                onLoadMore()
                Log.d("EndlessOnScrollListener", "loadMore()")
            }
        }
    }

    abstract fun onLoadMore()

}