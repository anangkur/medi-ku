package com.anangkur.mediku.feature.view.medicalRecords.detailMedicalRecord

import com.anangkur.mediku.feature.model.medical.MedicalRecordIntent

interface DetailMedicalRecordActionListener {
    fun onClickEdit(data: MedicalRecordIntent)
    fun onCLickImage(imageUrl: String)
}