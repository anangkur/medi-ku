package com.anangkur.mediku.feature.view.dashboard.main.home

import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.feature.model.ArticleIntent

interface HomeActionListener {
    fun onClickCovid19()
    fun onClickNews(data: ArticleIntent)
    fun onClickMedicalRecord(data: MedicalRecord)
    fun onClickMenstrualPeriod()
    fun onClickCovid19Check()
    fun onClickMedicalRecords()
}