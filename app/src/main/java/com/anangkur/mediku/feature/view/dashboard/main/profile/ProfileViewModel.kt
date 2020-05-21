package com.anangkur.mediku.feature.view.dashboard.main.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository): ViewModel() {

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<User>()
    val errorGetProfile = MutableLiveData<String>()
    fun getUserProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUser(object: BaseFirebaseListener<User?>{
                override fun onLoading(isLoading: Boolean) {
                    progressGetProfile.postValue(isLoading)
                }
                override fun onSuccess(data: User?) {
                    successGetProfile.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorGetProfile.postValue(errorMessage)
                }
            })
        }
    }

    val progressLogout = MutableLiveData<Boolean>()
    val successLogout = MutableLiveData<Boolean>()
    val errorLogout = MutableLiveData<String>()
    fun logout(){
        CoroutineScope(Dispatchers.IO).launch {
            repository.logout(object: BaseFirebaseListener<Boolean>{
                override fun onLoading(isLoading: Boolean) {
                    progressLogout.postValue(isLoading)
                }
                override fun onSuccess(data: Boolean) {
                    successLogout.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorLogout.postValue(errorMessage)
                }
            })
        }
    }
}