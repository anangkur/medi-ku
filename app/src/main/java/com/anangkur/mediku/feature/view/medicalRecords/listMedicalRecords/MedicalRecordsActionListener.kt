package com.anangkur.mediku.feature.view.medicalRecords.listMedicalRecords

import com.anangkur.mediku.feature.model.medical.MedicalRecordIntent

interface MedicalRecordsActionListener {
    fun onClickAddMedicalRecord()
    fun onClickItem(data: MedicalRecordIntent)
    fun onClickCovid()
}