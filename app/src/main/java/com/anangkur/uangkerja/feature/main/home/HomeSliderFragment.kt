package com.anangkur.uangkerja.feature.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.util.setImageUrl
import kotlinx.android.synthetic.main.fragment_image_slider.view.*

class HomeSliderFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getString(BUNDLE_RESULT)
        data?.let {
            view.iv_slider.setImageUrl(it)
        }
    }

    companion object{

        const val BUNDLE_RESULT = "BUNDLE_RESULT"

        fun getInstance(data: String): HomeSliderFragment{
            val fragment = HomeSliderFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_RESULT, data)
            fragment.arguments = bundle
            return fragment
        }
    }
}