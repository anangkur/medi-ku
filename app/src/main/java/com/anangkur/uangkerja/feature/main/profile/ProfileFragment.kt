package com.anangkur.uangkerja.feature.main.profile

import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseFragment

class ProfileFragment: BaseFragment<ViewModel>(){
    override val mLayout: Int
        get() = R.layout.fragment_profile
    override val mViewModel: ViewModel?
        get() = null

    companion object{
        fun newInstance() = ProfileFragment()
    }
}