package com.anangkur.mediku.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V: ViewBinding, T: ViewModel>: Fragment(){

    lateinit var mLayout: V
    abstract val mViewModel: T?

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mLayout = setupView(container)
        return mLayout.root
    }

    abstract fun setupView(container: ViewGroup?): V
}