package com.anangkur.mediku.feature.addMedicalRecord

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.util.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddMedicalRecordViewModel(private val repository: Repository): ViewModel() {

    var selectedCategory: String? = null

    val progressAddMedicalRecord = MutableLiveData<Boolean>()
    val successAddMedicalRecord = MutableLiveData<Boolean>()
    val errorAddMedicalRecord = MutableLiveData<String>()
    fun addMedicalRecord(medicalRecord: MedicalRecord){
        CoroutineScope(Dispatchers.IO).launch {
            progressAddMedicalRecord.postValue(true)
            try {
                val user = repository.remoteRepository.firebaseAuth.currentUser
                repository.remoteRepository.firestore.collection(Const.COLLECTION_MEDICAL_RECORD)
                    .document(user?.uid?:"")
                    .collection(Const.COLLECTION_MEDICAL_RECORD)
                    .add(medicalRecord)
                    .addOnSuccessListener {
                        successAddMedicalRecord.postValue(true)
                    }
                    .addOnFailureListener { exception ->
                        errorAddMedicalRecord.postValue(exception.message)
                    }
            }catch (e: Exception){
                errorAddMedicalRecord.postValue(e.message)
            }finally {
                progressAddMedicalRecord.postValue(false)
            }
        }
    }

    fun createCategoryList(): List<String> =
        listOf(Const.CATEGORY_SICK, Const.CATEGORY_CHECKUP, Const.CATEGORY_HOSPITAL)
}