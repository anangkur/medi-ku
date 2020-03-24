package com.anangkur.mediku.feature.detailMedicalRecord

import com.anangkur.mediku.data.model.medical.MedicalRecord

interface DetailMedicalRecordActionListener {
    fun onClickEdit(data: MedicalRecord)
}