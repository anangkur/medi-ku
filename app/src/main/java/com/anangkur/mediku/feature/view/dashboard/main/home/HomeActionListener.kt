package com.anangkur.mediku.feature.view.dashboard.main.home

import com.anangkur.mediku.feature.model.medical.MedicalRecordIntent
import com.anangkur.mediku.feature.model.news.ArticleIntent

interface HomeActionListener {
    fun onClickCovid19()
    fun onClickNews(data: ArticleIntent)
    fun onClickMedicalRecord(data: MedicalRecordIntent)
    fun onClickMenstrualPeriod()
    fun onClickCovid19Check()
    fun onClickMedicalRecords()
}