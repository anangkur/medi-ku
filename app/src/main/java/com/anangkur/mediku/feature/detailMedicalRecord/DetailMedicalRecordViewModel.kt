package com.anangkur.mediku.feature.detailMedicalRecord

import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.medical.MedicalRecord

class DetailMedicalRecordViewModel(private val repository: Repository): ViewModel() {

    lateinit var detailMedicalRecord: MedicalRecord
}