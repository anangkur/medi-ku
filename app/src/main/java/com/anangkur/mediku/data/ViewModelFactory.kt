package com.anangkur.mediku.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anangkur.mediku.feature.addMedicalRecord.AddMedicalRecordViewModel
import com.anangkur.mediku.feature.editPassword.EditPasswordViewModel
import com.anangkur.mediku.feature.editProfile.EditProfileViewModel
import com.anangkur.mediku.feature.forgotPassword.ForgotPasswordViewModel
import com.anangkur.mediku.feature.home.HomeViewModel
import com.anangkur.mediku.feature.profile.ProfileViewModel
import com.anangkur.mediku.feature.signIn.SignInViewModel
import com.anangkur.mediku.feature.signUp.SignUpViewModel
import com.anangkur.mediku.feature.splash.SplashViewModel
import com.anangkur.mediku.util.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T  =
        with(modelClass) {
            when {
                isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(repository)
                isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel(repository)
                isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(repository)
                isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository)
                isAssignableFrom(EditProfileViewModel::class.java) -> EditProfileViewModel(repository)
                isAssignableFrom(EditPasswordViewModel::class.java) -> EditPasswordViewModel(repository)
                isAssignableFrom(ForgotPasswordViewModel::class.java) -> ForgotPasswordViewModel(repository)
                isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository)
                isAssignableFrom(AddMedicalRecordViewModel::class.java) -> AddMedicalRecordViewModel(repository)
                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object{
        @Volatile private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(ViewModelFactory::class.java){
            INSTANCE ?: ViewModelFactory(Injection.provideRepository(
                context,
                context.getSharedPreferences(Const.PREF_NAME, MODE_PRIVATE),
                FirebaseAuth.getInstance(),
                Firebase.firestore
            )).also { INSTANCE = it }
        }
    }
}