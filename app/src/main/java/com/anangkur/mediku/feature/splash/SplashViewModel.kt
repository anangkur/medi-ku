package com.anangkur.mediku.feature.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: Repository): ViewModel(){

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<Boolean>()
    val errorGetProfile = MutableLiveData<String>()
    fun getProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUser(object: BaseFirebaseListener<User?>{
                override fun onLoading(isLoading: Boolean) {
                    progressGetProfile.postValue(isLoading)
                }
                override fun onSuccess(data: User?) {
                    successGetProfile.postValue(true)
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