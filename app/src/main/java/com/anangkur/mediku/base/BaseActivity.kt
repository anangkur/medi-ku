package com.anangkur.mediku.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<V: ViewBinding, T: ViewModel?>: AppCompatActivity(){

    lateinit var mLayout: V
    abstract val mViewModel: T?
    abstract val mToolbar: Toolbar?
    abstract val mTitleToolbar: String?

    abstract fun setupView(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayout = setupView()
        setContentView(mLayout.root)

        setupToolbar(mToolbar)
    }

    private fun setupToolbar(toolbar: Toolbar?){
        toolbar?.let {
            setSupportActionBar(it)
            title = mTitleToolbar
            it.setNavigationOnClickListener { onBackPressed() }
        }
    }
}