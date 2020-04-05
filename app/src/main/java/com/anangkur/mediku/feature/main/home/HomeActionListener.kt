package com.anangkur.mediku.feature.main.home

import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.model.news.Article

interface HomeActionListener {
    fun onClickCovid19()
    fun onClickNews(data: Article)
    fun onClickMedicalRecord(data: MedicalRecord)
}