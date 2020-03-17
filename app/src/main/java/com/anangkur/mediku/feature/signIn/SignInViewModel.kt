package com.anangkur.mediku.feature.signIn

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: Repository): ViewModel() {

    val progressSignInLive = MutableLiveData<Boolean>()
    val resultSignInLive = MutableLiveData<FirebaseUser>()
    val errorSignInLive = MutableLiveData<String>()
    fun firebaseSignIn(email: String, password: String){
        CoroutineScope(Dispatchers.IO).launch{
            progressSignInLive.postValue(true)
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    progressSignInLive.postValue(false)
                    if (it.isSuccessful){
                        resultSignInLive.postValue(it.result?.user)
                    }else{
                        errorSignInLive.postValue(it.exception?.message)
                    }
                }
        }
    }

    val progressSignInGoogleLive = MutableLiveData<Boolean>()
    fun firebaseSignInWithGoogle(acct: GoogleSignInAccount?) {
        CoroutineScope(Dispatchers.IO).launch {
            progressSignInGoogleLive.postValue(true)
            val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{ task ->
                progressSignInGoogleLive.postValue(false)
                if (task.isSuccessful) {
                    resultSignInLive.postValue(task.result?.user)
                } else {
                    errorSignInLive.postValue(task.exception?.message)
                }
            }
        }
    }
}