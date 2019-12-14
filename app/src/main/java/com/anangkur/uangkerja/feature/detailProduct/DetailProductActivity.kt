package com.anangkur.uangkerja.feature.detailProduct

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.anangkur.uangkerja.BuildConfig
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseActivity
import com.anangkur.uangkerja.base.BaseErrorView
import com.anangkur.uangkerja.util.obtainViewModel
import kotlinx.android.synthetic.main.layout_toolbar.*
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.data.model.product.DetailProduct
import com.anangkur.uangkerja.util.gone
import com.anangkur.uangkerja.util.setImageUrl
import com.anangkur.uangkerja.util.visible
import kotlinx.android.synthetic.main.activity_detail_product.*

class DetailProductActivity: BaseActivity<DetailProductViewModel>() {

    override val mLayout: Int
        get() = R.layout.activity_detail_product
    override val mViewModel: DetailProductViewModel
        get() = obtainViewModel(DetailProductViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_detail_product)

    companion object{
        private const val EXTRA_PRODUCT_ID = "EXTRA_PRODUCT_ID"
        fun startctivity(context: Context, data: String){
            context.startActivity(Intent(context, DetailProductActivity::class.java)
                .putExtra(EXTRA_PRODUCT_ID, data)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.productId = intent.getStringExtra(EXTRA_PRODUCT_ID)?:""
        observeViewModel()
        mViewModel.getDetailProduct()
    }

    private fun observeViewModel(){
        mViewModel.apply {
            detailProductLiveData.observe(this@DetailProductActivity, Observer {
                when(it.status){
                    Result.Status.LOADING -> {
                        error_detail.visible()
                        error_detail.showProgress()
                        content.gone()
                    }
                    Result.Status.SUCCESS -> {
                        error_detail.endProgress()
                        error_detail.gone()
                        content.visible()
                        if (it.data?.data != null){
                            setupDataToView(it.data.data)
                        }
                    }
                    Result.Status.ERROR -> {
                        error_detail.endProgress()
                        error_detail.showError(
                            it.message?:getString(R.string.error_default),
                            errorType = BaseErrorView.ERROR_GENERAL)
                        error_detail.setRetryClickListener {
                            getDetailProduct()
                        }
                    }
                }
            })
        }
    }

    private fun setupDataToView(data: DetailProduct){
        iv_detail.setImageUrl("${BuildConfig.baseImageUrl}${data.image}")
        tv_title_detail.text = data.name
        tv_subtitle_detail.text = data.details
        tv_desc_detail.text = data.description
        setupStock(data.quantity)
    }

    private fun setupStock(stock: Int){
        if (stock > 0){
            tv_stok_detail.text = getString(R.string.label_instock)
            tv_stok_detail.background = ContextCompat.getDrawable(this, R.drawable.rect_rounded_100dp_solid_green)
        }else{
            tv_stok_detail.text = getString(R.string.label_outstok)
            tv_stok_detail.background = ContextCompat.getDrawable(this, R.drawable.rect_rounded_100dp_solid_red)
        }
    }
}
