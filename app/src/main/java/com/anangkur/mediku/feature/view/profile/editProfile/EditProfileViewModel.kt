package com.anangkur.mediku.feature.view.profile.editProfile

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: Repository): ViewModel() {

    var user: User? = null

    val progressEditProfile = MutableLiveData<Boolean>()
    val successEditProfile = MutableLiveData<User>()
    val errorEditProfile = MutableLiveData<String>()
    fun editProfile(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            repository.editProfile(user, object: BaseFirebaseListener<User>{
                override fun onLoading(isLoading: Boolean) {
                    progressEditProfile.postValue(isLoading)
                }
                override fun onSuccess(data: User) {
                    successEditProfile.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorEditProfile.postValue(errorMessage)
                }
            })
        }
    }

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<User>()
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
                    errorEditProfile.postValue(errorMessage)
                }
            })
        }
    }

    val progressUploadImage = MutableLiveData<Boolean>()
    val successUploadImage = MutableLiveData<Uri>()
    fun uploadImage(image: Uri){
        CoroutineScope(Dispatchers.IO).launch {
            repository.uploadImage(image, object: BaseFirebaseListener<Uri>{
                override fun onLoading(isLoading: Boolean) {
                    progressUploadImage.postValue(isLoading)
                }
                override fun onSuccess(data: Uri) {
                    successUploadImage.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorEditProfile.postValue(errorMessage)
                }
            })
        }
    }
}