package com.anangkur.mediku.feature.home

import com.anangkur.mediku.data.model.medical.MedicalRecord

interface HomeActionListener {
    fun onClickProfile()
    fun onClickAddMedicalRecord()
    fun onClickItem(data: MedicalRecord)
    fun onClickCovid()
}