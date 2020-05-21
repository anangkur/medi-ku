package com.anangkur.mediku.feature.view.medicalRecords.addMedicalRecord

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.util.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMedicalRecordViewModel(private val repository: Repository): ViewModel() {

    var selectedCategory: String? = null
    var selectedDate: String? = null
    var medicalRecord: MedicalRecord? = null
    var document: String? = null

    val progressAddMedicalRecord = MutableLiveData<Boolean>()
    val successAddMedicalRecord = MutableLiveData<MedicalRecord>()
    val errorAddMedicalRecord = MutableLiveData<String>()
    fun addMedicalRecord(medicalRecord: MedicalRecord){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addMedicalRecord(medicalRecord, object: BaseFirebaseListener<MedicalRecord>{
                override fun onLoading(isLoading: Boolean) {
                    progressAddMedicalRecord.postValue(isLoading)
                }
                override fun onSuccess(data: MedicalRecord) {
                    successAddMedicalRecord.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorAddMedicalRecord.postValue(errorMessage)
                }
            })
        }
    }

    fun createCategoryList(): List<String> = listOf(Const.CATEGORY_SICK, Const.CATEGORY_CHECKUP, Const.CATEGORY_HOSPITAL)

    val progressUploadDocument = MutableLiveData<Boolean>()
    val successUploadDocument = MutableLiveData<Uri>()
    fun uploadDocument(document: Uri){
        CoroutineScope(Dispatchers.IO).launch {
            repository.uploadDocument(document, object: BaseFirebaseListener<Uri>{
                override fun onLoading(isLoading: Boolean) {
                    progressUploadDocument.postValue(isLoading)
                }
                override fun onSuccess(data: Uri) {
                    successUploadDocument.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorAddMedicalRecord.postValue(errorMessage)
                }
            })
        }
    }
}