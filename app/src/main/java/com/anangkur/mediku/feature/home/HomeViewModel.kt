package com.anangkur.mediku.feature.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.util.Const
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository): ViewModel() {

    val progressGetMedicalRecord = MutableLiveData<Boolean>()
    val successGetMedicalRecord = MutableLiveData<MedicalRecord>()
    val errorGetMedicalRecord = MutableLiveData<String>()
    fun getMedicalRecord(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressGetMedicalRecord.postValue(true)
                successGetMedicalRecord.postValue(MedicalRecord())
            }catch (e: Exception){
                errorGetMedicalRecord.postValue(e.message)
            }finally {
                progressGetMedicalRecord.postValue(false)
            }
        }
    }

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<User>()
    val errorGetProfile = MutableLiveData<String>()
    fun getUserProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = repository.remoteRepository.firebaseAuth.currentUser
                progressGetProfile.postValue(true)
                repository.remoteRepository.firestore
                    .collection(Const.collectionUser)
                    .document(user?.uid?:"")
                    .get()
                    .addOnSuccessListener { result ->
                        successGetProfile.postValue(result.toObject<User>())
                    }
                    .addOnFailureListener { exception ->
                        errorGetProfile.postValue(exception.message)
                    }
            }catch (e: Exception){
                errorGetProfile.postValue(e.message)
            }finally {
                progressGetProfile.postValue(false)
            }
        }
    }
}