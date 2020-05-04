package com.anangkur.mediku.feature.medicalRecords.listMedicalRecords

import com.anangkur.mediku.data.model.medical.MedicalRecord

interface MedicalRecordsActionListener {
    fun onClickProfile()
    fun onClickAddMedicalRecord()
    fun onClickItem(data: MedicalRecord)
    fun onClickCovid()
}