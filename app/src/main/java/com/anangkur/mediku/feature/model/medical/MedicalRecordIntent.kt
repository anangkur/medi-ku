package com.anangkur.mediku.feature.model.medical

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicalRecordIntent(
    val category: String = "",
    val diagnosis: String = "",
    val bloodPressure: Int = 0,
    val bodyTemperature: Int = 0,
    val heartRate: Int = 0,
    val createdAt: String = "",
    val updateAt: String = "",
    val document: String? = null
): Parcelable