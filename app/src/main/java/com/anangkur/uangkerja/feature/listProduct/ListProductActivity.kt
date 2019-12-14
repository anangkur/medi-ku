package com.anangkur.uangkerja.feature.listProduct

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseActivity
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.feature.main.MainActivity
import com.anangkur.uangkerja.util.*
import kotlinx.android.synthetic.main.activity_list_product.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ListProductActivity: BaseActivity<ListProductViewModel>() {

    override val mLayout: Int
        get() = R.layout.activity_list_product
    override val mViewModel: ListProductViewModel
        get() = obtainViewModel(ListProductViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_list_product)

    private lateinit var mAdapter: ListProductAdapter

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, ListProductActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapter()
        observeViewModel()
        mViewModel.getListProduct()
        swipe_list_product.setOnRefreshListener { mViewModel.getListProduct() }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            listProductLiveData.observe(this@ListProductActivity, Observer {
                when(it.status){
                    Result.Status.LOADING -> {
                        swipe_list_product.startLoading()
                    }
                    Result.Status.SUCCESS -> {
                        swipe_list_product.stopLoading()
                        if (it.data?.data?.data != null){
                            Log.d("LIST_PRODUCT", it.data.data.data.size.toString())
                            mAdapter.setRecyclerData(it.data.data.data)
                        }else{
                            this@ListProductActivity.showSnackbarShort(getString(R.string.error_empty))
                        }
                    }
                    Result.Status.ERROR -> {
                        swipe_list_product.stopLoading()
                        this@ListProductActivity.showSnackbarShort(it.message?:getString(R.string.error_default))
                    }
                }
            })
        }
    }

    private fun setupAdapter(){
        mAdapter = ListProductAdapter()
        recycler_product.apply {
            adapter = mAdapter
            setupRecyclerViewGrid(this@ListProductActivity, 2)
        }
    }
}
