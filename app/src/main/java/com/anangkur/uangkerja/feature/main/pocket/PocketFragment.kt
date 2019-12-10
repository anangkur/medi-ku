package com.anangkur.uangkerja.feature.main.pocket

import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseFragment

class PocketFragment: BaseFragment<ViewModel>(){
    override val mLayout: Int
        get() = R.layout.fragment_pocket
    override val mViewModel: ViewModel?
        get() = null

    companion object{
        fun newInstance() = PocketFragment()
    }
}