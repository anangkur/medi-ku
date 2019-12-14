package com.anangkur.uangkerja.feature.listProduct

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseActivity
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.feature.detailProduct.DetailProductActivity
import com.anangkur.uangkerja.feature.main.MainActivity
import com.anangkur.uangkerja.util.*
import kotlinx.android.synthetic.main.activity_list_product.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ListProductActivity: BaseActivity<ListProductViewModel>(), ListProductActionListener {

    override val mLayout: Int
        get() = R.layout.activity_list_product
    override val mViewModel: ListProductViewModel
        get() = obtainViewModel(ListProductViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_list_product)

    private lateinit var mAdapter: ListProductAdapter
    private lateinit var mCategoryAdapter: ListCategoryAdapter

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, ListProductActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdapter()
        setupCategoryAdapter()
        observeViewModel()
        mViewModel.getListProduct(1)
        mViewModel.getListCategory()
        setupCategoryActive()
        swipe_list_product.setOnRefreshListener {
            mViewModel.getListProduct(1)
            mViewModel.getListCategory()
            setupCategoryActive()
        }
        btn_delete_category.setOnClickListener { this.onClickDeleteCategory() }
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
            listCategoryLiveData.observe(this@ListProductActivity, Observer {
                when(it.status){
                    Result.Status.LOADING -> {
                        swipe_list_product.startLoading()
                    }
                    Result.Status.SUCCESS -> {
                        swipe_list_product.stopLoading()
                        if (it.data?.data != null){
                            mCategoryAdapter.setRecyclerData(it.data.data)
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
        mAdapter = ListProductAdapter(this)
        recycler_product.apply {
            adapter = mAdapter
            setupRecyclerViewGrid(this@ListProductActivity, 2)
        }
    }

    private fun setupCategoryAdapter(){
        mCategoryAdapter = ListCategoryAdapter(this)
        recycler_category.apply {
            adapter = mCategoryAdapter
            setupRecyclerViewLinear(this@ListProductActivity, LinearLayoutManager.HORIZONTAL)
        }
    }

    private fun setupCategoryActive(){
        if (mViewModel.categoryActive != null){
            layout_category_active.visible()
            tv_category_active.text = mViewModel.categoryActive
        }else{
            layout_category_active.gone()
        }
    }

    override fun onClickItem(productId: String) {
        DetailProductActivity.startctivity(this, productId)
    }

    override fun onClickCategory(categoryId: Int, categoryName: String) {
        mViewModel.category = categoryId
        mViewModel.categoryActive = "${getString(R.string.text_category)}: $categoryName"
        mViewModel.getListProduct(1)
        setupCategoryActive()
    }

    override fun onClickDeleteCategory() {
        mViewModel.categoryActive = null
        mViewModel.category = null
        setupCategoryActive()
        mViewModel.getListProduct(1)
    }
}
