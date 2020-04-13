package com.anangkur.mediku.feature.auth.editPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditPasswordViewModel(private val repository: Repository): ViewModel() {

    val progressEditPassword = MutableLiveData<Boolean>()
    val successEditPassword = MutableLiveData<Boolean>()
    val errorEditPassword = MutableLiveData<String>()
    val errorAuth = MutableLiveData<String>()
    fun editPassword(oldPassword: String?, newPassword: String?){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressEditPassword.postValue(true)
                val user = repository.remoteRepository.firebaseAuth.currentUser
                val credential = EmailAuthProvider.getCredential(user?.email?:"", oldPassword?:"")
                user?.reauthenticate(credential)?.addOnCompleteListener {
                    progressEditPassword.postValue(false)
                    if (it.isSuccessful){
                        user.updatePassword(newPassword?:"").addOnCompleteListener {task ->
                            if (task.isSuccessful){
                                successEditPassword.postValue(true)
                            }else{
                                errorEditPassword.postValue(task.exception?.message)
                            }
                        }
                    }else{
                        errorAuth.postValue(it.exception?.message)
                    }
                }
            }catch (e: Exception){
                progressEditPassword.postValue(false)
                errorEditPassword.postValue(e.message)
            }
        }
    }
}