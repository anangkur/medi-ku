package com.anangkur.uangkerja.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.data.Repository
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.data.model.auth.ParamLogin
import com.anangkur.uangkerja.data.model.auth.ResponseLogin
import com.anangkur.uangkerja.util.validateEmail
import com.anangkur.uangkerja.util.validatePassword

class LoginViewModel(private val repository: Repository): ViewModel(){
    var isLoading = false

    private val reloadTrigger = MutableLiveData<ParamLogin>()
    val loginLiveData: LiveData<Result<ResponseLogin>> = Transformations.switchMap(reloadTrigger){
        repository.postLogin(it.email, it.password)
    }

    fun login(email: String, password: String){
        if (email.validateEmail() && password.validatePassword() && !isLoading){
            reloadTrigger.value = ParamLogin(email, password)
        }
    }

    fun saveApiToken(apiToken: String){
        repository.saveApiToken(apiToken)
    }
}