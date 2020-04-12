package com.anangkur.mediku.feature.medicalRecords.addMedicalRecord

interface AddMedicalActionListener {
    fun onClickSave(
        category: String,
        diagnose: String?,
        bloodPressure: String?,
        bodyTemperature: String?,
        heartRate: String?,
        date: String?)
    fun onClickCategory()
    fun onClickImage()
    fun onClickDate()
}