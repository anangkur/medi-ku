package com.anangkur.mediku.feature.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.util.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository): ViewModel() {

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

    val progressLogout = MutableLiveData<Boolean>()
    val successLogout = MutableLiveData<Boolean>()
    val errorLogout = MutableLiveData<String>()
    fun logout(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressLogout.postValue(true)
                FirebaseAuth.getInstance().signOut()
                successLogout.postValue(true)
            }catch (e: Exception){
                errorLogout.postValue(e.message)
            }finally {
                progressLogout.postValue(false)
            }
        }
    }
}