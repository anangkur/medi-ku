package com.anangkur.mediku.feature.mapper

import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.feature.model.medical.MedicalRecordIntent

class MedicalRecordMapper: IntentMapper<MedicalRecordIntent, MedicalRecord> {

    companion object{
        private var INSTANCE: MedicalRecordMapper? = null
        fun getInstance() = INSTANCE ?: MedicalRecordMapper()
    }

    override fun mapToIntent(data: MedicalRecord): MedicalRecordIntent {
        return MedicalRecordIntent(
            category = data.category,
            bloodPressure = data.bloodPressure,
            bodyTemperature = data.bodyTemperature,
            createdAt = data.createdAt,
            diagnosis = data.diagnosis,
            document = data.document,
            heartRate = data.heartRate,
            updateAt = data.updateAt
        )
    }

    override fun mapFromIntent(data: MedicalRecordIntent): MedicalRecord {
        return MedicalRecord(
            category = data.category,
            bloodPressure = data.bloodPressure,
            bodyTemperature = data.bodyTemperature,
            createdAt = data.createdAt,
            diagnosis = data.diagnosis,
            document = data.document,
            heartRate = data.heartRate,
            updateAt = data.updateAt
        )
    }
}