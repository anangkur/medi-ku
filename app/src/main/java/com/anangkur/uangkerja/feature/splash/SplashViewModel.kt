package com.anangkur.uangkerja.feature.splash

import androidx.lifecycle.ViewModel
import com.anangkur.uangkerja.data.Repository

class SplashViewModel(private val repository: Repository): ViewModel(){
    fun isLoggedIn() = !repository.loadApiToken().isNullOrEmpty()
}