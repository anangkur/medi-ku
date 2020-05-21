package com.anangkur.mediku.feature.view.medicalRecords.detailMedicalRecord

import com.anangkur.mediku.data.model.medical.MedicalRecord

interface DetailMedicalRecordActionListener {
    fun onClickEdit(data: MedicalRecord)
    fun onCLickImage(imageUrl: String)
}