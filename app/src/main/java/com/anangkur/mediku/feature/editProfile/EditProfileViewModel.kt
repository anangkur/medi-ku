package com.anangkur.mediku.feature.editProfile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel(private val repository: Repository): ViewModel() {

    val progressEditProfile = MutableLiveData<Boolean>()
    val successEditProfile = MutableLiveData<Void>()
    val errorEditProfile = MutableLiveData<String>()
    fun editProfile(name: String){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressEditProfile.postValue(true)
                val userUpdateRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                FirebaseAuth.getInstance().currentUser?.updateProfile(userUpdateRequest)
                    ?.addOnCompleteListener {
                        if (it.isSuccessful){
                            successEditProfile.postValue(it.result)
                        }else{
                            errorEditProfile.postValue(it.exception?.message)
                        }
                    }
            }catch (e: Exception){
                errorEditProfile.postValue(e.message)
            }finally {
                progressEditProfile.postValue(false)
            }
        }
    }

    val progressGetProfile = MutableLiveData<Boolean>()
    val successGetProfile = MutableLiveData<Pair<Boolean, FirebaseUser?>>()
    val errorGetProfile = MutableLiveData<String>()
    fun getUserProfile(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressGetProfile.postValue(true)
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser != null){
                    successGetProfile.postValue(Pair(true, currentUser))
                }else{
                    successGetProfile.postValue(Pair(false, null))
                }
            }catch (e: Exception){
                errorGetProfile.postValue(e.message)
            }finally {
                progressGetProfile.postValue(false)
            }
        }
    }
}