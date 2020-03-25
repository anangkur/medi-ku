package com.anangkur.mediku.feature.addMedicalRecord

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.util.Const
import com.anangkur.mediku.util.getCurrentTimeStamp
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
            try {
                progressAddMedicalRecord.postValue(true)
                val user = repository.remoteRepository.firebaseAuth.currentUser
                repository.remoteRepository.firestore.collection(Const.COLLECTION_MEDICAL_RECORD)
                    .document(user?.uid?:"")
                    .collection(Const.COLLECTION_MEDICAL_RECORD)
                    .document(medicalRecord.createdAt)
                    .set(medicalRecord)
                    .addOnSuccessListener {
                        progressAddMedicalRecord.postValue(false)
                        successAddMedicalRecord.postValue(medicalRecord)
                    }
                    .addOnFailureListener { exception ->
                        progressAddMedicalRecord.postValue(false)
                        errorAddMedicalRecord.postValue(exception.message)
                    }
            }catch (e: Exception){
                progressAddMedicalRecord.postValue(false)
                errorAddMedicalRecord.postValue(e.message)
            }
        }
    }

    fun createCategoryList(): List<String> =
        listOf(Const.CATEGORY_SICK, Const.CATEGORY_CHECKUP, Const.CATEGORY_HOSPITAL)

    val progressUploadDocument = MutableLiveData<Boolean>()
    val successUploadDocument = MutableLiveData<Uri>()
    fun uploadDocument(document: Uri){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressUploadDocument.postValue(true)
                val fileName = document.lastPathSegment?:""
                val extension = fileName.substring(fileName.lastIndexOf("."))
                val user = repository.remoteRepository.firebaseAuth.currentUser
                val storageReference = repository.remoteRepository.storage.reference
                    .child(Const.COLLECTION_MEDICAL_RECORD)
                    .child(user?.uid?:"")
                    .child("${user?.uid}_${getCurrentTimeStamp() ?:"1990-01-01 00:00:00"}$extension")
                storageReference.putFile(document)
                    .addOnProgressListener {
                        progressUploadDocument.postValue(true)
                    }.continueWithTask { task ->
                        if (!task.isSuccessful) {
                            throw task.exception!!
                        }
                        storageReference.downloadUrl
                    }.addOnSuccessListener {
                        progressUploadDocument.postValue(false)
                        successUploadDocument.postValue(it)
                    }
                    .addOnFailureListener {
                        progressUploadDocument.postValue(false)
                        errorAddMedicalRecord.postValue(it.message)
                    }
            }catch (e: Exception){
                progressUploadDocument.postValue(false)
                errorAddMedicalRecord.postValue(e.message)
            }
        }
    }
}