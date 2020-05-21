package com.anangkur.mediku.data.remote.model.medical

data class MedicalRecordRemoteModel(
    val category: String = "",
    val diagnosis: String = "",
    val bloodPressure: Int = 0,
    val bodyTemperature: Int = 0,
    val heartRate: Int = 0,
    val createdAt: String = "",
    val updateAt: String = "",
    val document: String? = null
)