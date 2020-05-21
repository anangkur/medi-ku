package com.anangkur.mediku.feature.view.medicalRecords.listMedicalRecords

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.medical.MedicalRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MedicalRecordsViewModel(private val repository: Repository): ViewModel() {

    val progressGetMedicalRecord = MutableLiveData<Boolean>()
    val successGetMedicalRecord = MutableLiveData<List<MedicalRecord>>()
    val errorGetMedicalRecord = MutableLiveData<String>()
    fun getMedicalRecord(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getMedicalRecords(object: BaseFirebaseListener<List<MedicalRecord>> {
                override fun onLoading(isLoading: Boolean) {
                    progressGetMedicalRecord.postValue(isLoading)
                }
                override fun onSuccess(data: List<MedicalRecord>) {
                    successGetMedicalRecord.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorGetMedicalRecord.postValue(errorMessage)
                }
            })
        }
    }
}