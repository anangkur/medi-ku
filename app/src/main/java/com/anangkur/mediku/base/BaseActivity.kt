package com.anangkur.mediku.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T: ViewModel>: AppCompatActivity(){

    @get:LayoutRes
    abstract val mLayout: Int
    abstract val mViewModel: T
    abstract val mToolbar: Toolbar?
    abstract val mTitleToolbar: String?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mLayout)

        setupToolbar()
    }

    private fun setupToolbar(){
        setSupportActionBar(mToolbar)
        supportActionBar?.title = mTitleToolbar
        mToolbar?.setNavigationOnClickListener { onBackPressed() }
    }
}