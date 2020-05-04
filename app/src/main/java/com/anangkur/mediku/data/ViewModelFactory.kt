package com.anangkur.mediku.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anangkur.mediku.data.local.room.AppDatabase
import com.anangkur.mediku.data.remote.Covid19ApiService
import com.anangkur.mediku.data.remote.NewCovid19ApiService
import com.anangkur.mediku.feature.about.AboutViewModel
import com.anangkur.mediku.feature.medicalRecords.addMedicalRecord.AddMedicalRecordViewModel
import com.anangkur.mediku.feature.covid.covid19.CovidViewModel
import com.anangkur.mediku.feature.covid.covid19Detail.Covid19DetailViewModel
import com.anangkur.mediku.feature.medicalRecords.detailMedicalRecord.DetailMedicalRecordViewModel
import com.anangkur.mediku.feature.auth.editPassword.EditPasswordViewModel
import com.anangkur.mediku.feature.profile.editProfile.EditProfileViewModel
import com.anangkur.mediku.feature.auth.forgotPassword.ForgotPasswordViewModel
import com.anangkur.mediku.feature.medicalRecords.listMedicalRecords.MedicalRecordsViewModel
import com.anangkur.mediku.feature.dashboard.main.home.HomeViewModel as HomeViewModelFragment
import com.anangkur.mediku.feature.dashboard.main.MainViewModel
import com.anangkur.mediku.feature.mens.menstrual.MenstrualViewModel
import com.anangkur.mediku.feature.mens.menstrualEdit.MenstrualEditViewModel
import com.anangkur.mediku.feature.profile.userProfile.ProfileViewModel
import com.anangkur.mediku.feature.dashboard.main.profile.ProfileViewModel as ProfileViewModelFragment
import com.anangkur.mediku.feature.auth.signIn.SignInViewModel
import com.anangkur.mediku.feature.auth.signUp.SignUpViewModel
import com.anangkur.mediku.feature.splash.SplashViewModel
import com.anangkur.mediku.util.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ViewModelFactory(private val repository: Repository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T  =
        with(modelClass) {
            when {
                isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(repository)

                isAssignableFrom(SignInViewModel::class.java) -> SignInViewModel(repository)
                isAssignableFrom(SignUpViewModel::class.java) -> SignUpViewModel(repository)
                isAssignableFrom(EditProfileViewModel::class.java) -> EditProfileViewModel(repository)
                isAssignableFrom(EditPasswordViewModel::class.java) -> EditPasswordViewModel(repository)
                isAssignableFrom(ForgotPasswordViewModel::class.java) -> ForgotPasswordViewModel(repository)

                isAssignableFrom(ProfileViewModelFragment::class.java) -> ProfileViewModelFragment(repository)
                isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository)

                isAssignableFrom(HomeViewModelFragment::class.java) -> HomeViewModelFragment(repository)
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository)
                isAssignableFrom(MedicalRecordsViewModel::class.java) -> MedicalRecordsViewModel(repository)

                isAssignableFrom(AddMedicalRecordViewModel::class.java) -> AddMedicalRecordViewModel(repository)
                isAssignableFrom(DetailMedicalRecordViewModel::class.java) -> DetailMedicalRecordViewModel(repository)

                isAssignableFrom(CovidViewModel::class.java) -> CovidViewModel(repository)
                isAssignableFrom(Covid19DetailViewModel::class.java) -> Covid19DetailViewModel(repository)

                isAssignableFrom(MenstrualViewModel::class.java) -> MenstrualViewModel(repository)
                isAssignableFrom(MenstrualEditViewModel::class.java) -> MenstrualEditViewModel(repository)

                isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel(repository)

                else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T

    companion object{
        @Volatile private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(ViewModelFactory::class.java){
            INSTANCE ?: ViewModelFactory(Injection.provideRepository(
                context.getSharedPreferences(Const.PREF_NAME, MODE_PRIVATE),
                FirebaseAuth.getInstance(),
                Firebase.firestore,
                FirebaseStorage.getInstance(),
                AppDatabase.getDatabase(context).getDao(),
                Covid19ApiService.getCovid19ApiService,
                NewCovid19ApiService.getCovid19ApiService
            )).also { INSTANCE = it }
        }
    }
}