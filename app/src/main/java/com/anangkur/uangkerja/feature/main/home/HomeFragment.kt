package com.anangkur.uangkerja.feature.main.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseFragment
import com.anangkur.uangkerja.base.BaseSliderAdapter
import com.anangkur.uangkerja.util.disableClickTablayout
import com.anangkur.uangkerja.util.obtainViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment<ViewModel>(){
    override val mLayout: Int
        get() = R.layout.fragment_home
    override val mViewModel: ViewModel?
        get() = obtainViewModel(HomeViewModel::class.java)

    companion object{
        fun newInstance() = HomeFragment()
    }

    private lateinit var pagerAdapter: BaseSliderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPagerSlider()
        setupSliderFragment()
    }

    private fun setupViewPagerSlider(){
        pagerAdapter = BaseSliderAdapter(childFragmentManager)
    }

    private fun setupSliderPage(pagerAdapter: BaseSliderAdapter){
        vp_slider.adapter = pagerAdapter
        tab_slider.setupWithViewPager(vp_slider, true)
        tab_slider.disableClickTablayout()
    }

    private fun setupSliderFragment(){
        for (i in 0 until 5){
            pagerAdapter.addFragment(HomeSliderFragment.getInstance("https://picsum.photos/200/400"))
        }
        setupSliderPage(pagerAdapter)
    }
}