package com.anangkur.uangkerja.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class BaseSliderAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val listFragment = ArrayList<Fragment>()

    override fun getCount(): Int = listFragment.size
    override fun getItem(position: Int): Fragment = listFragment[position]

    fun addFragment(fragment: Fragment){
        listFragment.add(fragment)
    }
}