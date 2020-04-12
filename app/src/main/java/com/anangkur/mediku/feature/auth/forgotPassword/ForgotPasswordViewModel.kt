package com.anangkur.mediku.feature.auth.forgotPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
            try {
                progressForgotPassword.postValue(true)
                repository.remoteRepository.firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        progressForgotPassword.postValue(false)
                        if (it.isSuccessful){
                            successForgotPassword.postValue("Email sent!")
                        }else{
                            errorForgotPassword.postValue(it.exception?.message)
                        }
                    }
            }catch (e: Exception){
                progressForgotPassword.postValue(false)
                errorForgotPassword.postValue(e.message)
            }
        }
    }
}