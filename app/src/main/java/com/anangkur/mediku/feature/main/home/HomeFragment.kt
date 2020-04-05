package com.anangkur.mediku.feature.main.home

import android.os.Bundle
import android.view.View
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseFragment
import com.anangkur.mediku.feature.covid19.CovidActivity
import com.anangkur.mediku.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment<HomeViewModel>(), HomeActionListener {

    companion object{
        fun newInstance() = HomeFragment()
    }

    override val mLayout: Int
        get() = R.layout.fragment_home
    override val mViewModel: HomeViewModel?
        get() = obtainViewModel(HomeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        card_covid.setOnClickListener { this.onClickCovid19() }
    }

    override fun onClickCovid19() {
        CovidActivity.startActivity(requireContext())
    }
}