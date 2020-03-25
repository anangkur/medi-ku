package com.anangkur.mediku.feature.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.util.Const
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository): ViewModel() {

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<User>()
    val errorGetProfile = MutableLiveData<String>()
    fun getUserProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = repository.remoteRepository.firebaseAuth.currentUser
                progressGetProfile.postValue(true)
                repository.remoteRepository.firestore
                    .collection(Const.COLLECTION_USER)
                    .document(user?.uid?:"")
                    .get()
                    .addOnSuccessListener { result ->
                        progressGetProfile.postValue(false)
                        successGetProfile.postValue(result.toObject<User>())
                    }
                    .addOnFailureListener { exception ->
                        progressGetProfile.postValue(false)
                        errorGetProfile.postValue(exception.message)
                    }
            }catch (e: Exception){
                progressGetProfile.postValue(false)
                errorGetProfile.postValue(e.message)
            }
        }
    }

    val progressLogout = MutableLiveData<Boolean>()
    val successLogout = MutableLiveData<Boolean>()
    val errorLogout = MutableLiveData<String>()
    fun logout(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressLogout.postValue(true)
                repository.remoteRepository.firebaseAuth.signOut()
                progressLogout.postValue(false)
                successLogout.postValue(true)
            }catch (e: Exception){
                progressLogout.postValue(false)
                errorLogout.postValue(e.message)
            }
        }
    }
}