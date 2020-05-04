package com.anangkur.mediku.feature.dashboard.main.home

import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.model.news.Article

interface HomeActionListener {
    fun onClickCovid19()
    fun onClickNews(data: Article)
    fun onClickMedicalRecord(data: MedicalRecord)
    fun onClickMenstrualPeriod()
    fun onClickCovid19Check()
    fun onClickMedicalRecords()
}