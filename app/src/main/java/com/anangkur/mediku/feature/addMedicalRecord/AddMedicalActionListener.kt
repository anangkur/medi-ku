package com.anangkur.mediku.feature.addMedicalRecord

interface AddMedicalActionListener {
    fun onClickSave(
        category: String,
        diagnose: String?,
        bloodPressure: String?,
        bodyTemperature: String?,
        heartRate: String?)
    fun onClickCategory()
    fun onClickImage()
}