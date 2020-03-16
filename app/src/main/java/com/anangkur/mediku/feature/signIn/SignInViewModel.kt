package com.anangkur.mediku.feature.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: Repository): ViewModel() {

    val progressSignUpLive = MutableLiveData<Boolean>()
    val resultSignUpLive = MutableLiveData<FirebaseUser>()
    val errorSignUpLive = MutableLiveData<String>()
    fun firebaseSignIn(email: String, password: String){
        CoroutineScope(Dispatchers.IO).launch{
            progressSignUpLive.postValue(true)
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    progressSignUpLive.postValue(false)
                    if (it.isSuccessful){
                        resultSignUpLive.postValue(it.result?.user)
                    }else{
                        errorSignUpLive.postValue(it.exception?.message)
                    }
                }
        }
    }
}