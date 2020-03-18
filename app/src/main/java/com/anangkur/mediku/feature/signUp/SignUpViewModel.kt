package com.anangkur.mediku.feature.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: Repository): ViewModel() {
    val progressSignUpLive = MutableLiveData<Boolean>()
    val resultSignUpLive = MutableLiveData<FirebaseUser>()
    val errorSignUpLive = MutableLiveData<String>()
    fun firebaseSignUp(name: String, email: String, password: String){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                progressSignUpLive.postValue(true)
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            val profileUpdate = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                            it.result?.user?.updateProfile(profileUpdate)?.addOnCompleteListener {updateProfile ->
                                if (updateProfile.isSuccessful){
                                    resultSignUpLive.postValue(it.result?.user)
                                }else{
                                    errorSignUpLive.postValue(updateProfile.exception?.message)
                                }
                            }
                        }else{
                            errorSignUpLive.postValue(it.exception?.message)
                        }
                    }
            }catch (e: Exception){
                errorSignUpLive.postValue(e.message)
            }finally {
                progressSignUpLive.postValue(false)
            }
        }
    }
}