package com.anangkur.mediku.feature.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: Repository): ViewModel(){

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<Boolean>()
    val errorGetProfile = MutableLiveData<String>()
    fun checkLogin(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.checkUserLogin(object: BaseFirebaseListener<Boolean>{
                override fun onLoading(isLoading: Boolean) {
                    progressGetProfile.postValue(isLoading)
                }
                override fun onSuccess(data: Boolean) {
                    successGetProfile.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorGetProfile.postValue(errorMessage)
                }
            })
        }
    }

    fun saveCountry(country: String){
        repository.saveCountry(country)
    }

    fun saveFirebaseToken(firebaseToken: String){
        repository.saveFirebaseToken(firebaseToken)
    }
}