package com.anangkur.mediku.feature.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: Repository): ViewModel(){

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<Boolean>()
    val errorGetProfile = MutableLiveData<String>()
    fun getProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressGetProfile.postValue(true)
                val user = repository.remoteRepository.firebaseAuth.currentUser
                if (user != null){
                    successGetProfile.postValue(true)
                }else{
                    successGetProfile.postValue(false)
                }
                progressGetProfile.postValue(false)
            }catch (e: Exception){
                progressGetProfile.postValue(false)
                errorGetProfile.postValue(e.message?:"")
            }
        }
    }

    fun saveCountry(country: String){
        Log.d("saveCountry", country)
        repository.saveCountry(country)
    }
}