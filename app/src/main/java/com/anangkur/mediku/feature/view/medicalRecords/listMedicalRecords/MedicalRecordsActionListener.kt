package com.anangkur.mediku.feature.view.medicalRecords.listMedicalRecords

import com.anangkur.mediku.data.model.medical.MedicalRecord

interface MedicalRecordsActionListener {
    fun onClickAddMedicalRecord()
    fun onClickItem(data: MedicalRecord)
    fun onClickCovid()
}