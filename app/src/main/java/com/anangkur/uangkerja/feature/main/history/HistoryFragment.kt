package com.anangkur.uangkerja.feature.main.history

import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseFragment

class HistoryFragment: BaseFragment<ViewModel>(){
    override val mLayout: Int
        get() = R.layout.fragment_history
    override val mViewModel: ViewModel?
        get() = null

    companion object{
        fun newInstance() = HistoryFragment()
    }
}