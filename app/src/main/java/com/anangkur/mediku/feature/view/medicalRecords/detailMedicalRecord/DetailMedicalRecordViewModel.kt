package com.anangkur.mediku.feature.view.medicalRecords.detailMedicalRecord

import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.feature.model.medical.MedicalRecordIntent

class DetailMedicalRecordViewModel(private val repository: Repository): ViewModel() {

    lateinit var detailMedicalRecord: MedicalRecordIntent
}