package com.anangkur.uangkerja.feature.main.topup

import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseFragment

class TopupFragment: BaseFragment<ViewModel>(){
    override val mLayout: Int
        get() = R.layout.fragment_topup
    override val mViewModel: ViewModel?
        get() = null

    companion object{
        fun newInstance() = TopupFragment()
    }
}