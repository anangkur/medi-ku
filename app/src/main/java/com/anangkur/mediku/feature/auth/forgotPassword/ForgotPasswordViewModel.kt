package com.anangkur.mediku.feature.auth.forgotPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(private val repository: Repository): ViewModel() {

    val progressForgotPassword = MutableLiveData<Boolean>()
    val successForgotPassword = MutableLiveData<String>()
    val errorForgotPassword = MutableLiveData<String>()
    fun sendResetEmail(email: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.resetPassword(email, object: BaseFirebaseListener<String>{
                override fun onLoading(isLoading: Boolean) {
                    progressForgotPassword.postValue(isLoading)
                }
                override fun onSuccess(data: String) {
                    successForgotPassword.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorForgotPassword.postValue(errorMessage)
                }
            })
        }
    }
}