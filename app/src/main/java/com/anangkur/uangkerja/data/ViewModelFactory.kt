package com.anangkur.uangkerja.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anangkur.uangkerja.feature.listProduct.ListProductViewModel
import com.anangkur.uangkerja.feature.login.LoginViewModel
import com.anangkur.uangkerja.feature.main.home.HomeViewModel
import com.anangkur.uangkerja.feature.register.RegisterViewModel
import com.anangkur.uangkerja.feature.splash.SplashViewModel
import com.anangkur.uangkerja.util.Const

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T  =
        with(modelClass) {
            when {
                isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(repository)
                isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository)
                isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(repository)
                isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository)
                isAssignableFrom(ListProductViewModel::class.java) -> ListProductViewModel(repository)
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object{
        @Volatile private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(ViewModelFactory::class.java){
            INSTANCE ?: ViewModelFactory(Injection.provideRepository(context, context.getSharedPreferences(Const.PREF_NAME, MODE_PRIVATE))).also { INSTANCE = it }
        }
    }
}