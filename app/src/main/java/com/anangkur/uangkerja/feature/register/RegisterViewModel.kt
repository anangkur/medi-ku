package com.anangkur.uangkerja.feature.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.data.Repository
import com.anangkur.uangkerja.data.model.BaseResponse
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.data.model.auth.ParamRegister
import com.anangkur.uangkerja.data.model.auth.Register
import com.anangkur.uangkerja.util.validateEmail
import com.anangkur.uangkerja.util.validateName
import com.anangkur.uangkerja.util.validatePassword
import com.anangkur.uangkerja.util.validatePasswordConfirm

class RegisterViewModel(private val repository: Repository): ViewModel(){
    var isLoading = false

    private val reloadTrigger = MutableLiveData<ParamRegister>()
    val registerLiveData: LiveData<Result<BaseResponse<Register>>> = Transformations.switchMap(reloadTrigger){
        repository.postRegister(it.name, it.email, it.password, it.passwordConfirm)
    }

    fun register(name: String, email: String, password: String, paswordConfirm: String){
        if (name.validateName()
            && email.validateEmail()
            && password.validatePassword()
            && paswordConfirm.validatePasswordConfirm(password)
            && !isLoading){
            reloadTrigger.value = ParamRegister(name, email, password, paswordConfirm)
        }
    }
}