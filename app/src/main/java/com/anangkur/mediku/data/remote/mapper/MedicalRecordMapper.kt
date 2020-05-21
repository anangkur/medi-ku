package com.anangkur.mediku.data.remote.mapper

import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.remote.model.medical.MedicalRecordRemoteModel

class MedicalRecordMapper: RemoteMapper<MedicalRecordRemoteModel, MedicalRecord> {

    companion object{
        private var INSTANCE: MedicalRecordMapper? = null
        fun getInstance() = INSTANCE ?: MedicalRecordMapper()
    }

    override fun mapFromRemote(data: MedicalRecordRemoteModel): MedicalRecord {
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

    override fun mapToRemote(data: MedicalRecord): MedicalRecordRemoteModel {
        return MedicalRecordRemoteModel(
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