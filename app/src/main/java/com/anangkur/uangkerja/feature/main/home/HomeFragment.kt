package com.anangkur.uangkerja.feature.main.home

import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseFragment

class HomeFragment: BaseFragment<ViewModel>(){
    override val mLayout: Int
        get() = R.layout.fragment_home
    override val mViewModel: ViewModel?
        get() = null

    companion object{
        fun newInstance() = HomeFragment()
    }
}