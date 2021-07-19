package com.anangkur.mediku.feature.view.mens.menstrualEdit

interface MenstrualEditActionListener {
    fun onClickLastPeriod()
    fun onCLickSave(lastPeriod: String?, periodLong: String?, cycleLong: String?, isEdit: Boolean)
}