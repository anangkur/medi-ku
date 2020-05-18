package com.anangkur.mediku.feature.auth.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.util.Const
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: Repository): ViewModel() {

    val progressSignUpLive = MutableLiveData<Boolean>()
    val errorSignUpLive = MutableLiveData<String>()
    fun firebaseSignUp(name: String, email: String, password: String){
        CoroutineScope(Dispatchers.IO).launch{
            repository.signUp(name, email, password, object: BaseFirebaseListener<FirebaseUser>{
                override fun onLoading(isLoading: Boolean) {
                    progressSignUpLive.postValue(isLoading)
                }
                override fun onSuccess(data: FirebaseUser) {
                    getUser(data, true)
                }
                override fun onFailed(errorMessage: String) {
                    errorSignUpLive.postValue(errorMessage)
                }
            })
        }
    }

    val progressSignUpGoogleLive = MutableLiveData<Boolean>()
    fun firebaseSignUpWithGoogle(acct: GoogleSignInAccount?) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.signInWithGoogle(acct, object: BaseFirebaseListener<FirebaseUser>{
                override fun onLoading(isLoading: Boolean) {
                    progressSignUpGoogleLive.postValue(isLoading)
                }
                override fun onSuccess(data: FirebaseUser) {
                    getUser(data, false)
                }
                override fun onFailed(errorMessage: String) {
                    errorSignUpLive.postValue(errorMessage)
                }
            })
        }
    }

    val successCreateUser = MutableLiveData<User>()
    private fun createUser(user: FirebaseUser, isPassword: Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            repository.createUser(user, loadFirebaseToken(), object: BaseFirebaseListener<User>{
                override fun onLoading(isLoading: Boolean) {
                    if (isPassword){
                        progressSignUpLive.postValue(isLoading)
                    }else{
                        progressSignUpGoogleLive.postValue(isLoading)
                    }
                }
                override fun onSuccess(data: User) {
                    successCreateUser.postValue(data)
                }
                override fun onFailed(errorMessage: String) {
                    errorSignUpLive.postValue(errorMessage)
                }
            })
        }
    }

    private fun getUser(user: FirebaseUser, isPassword: Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            repository.getUser(user, object: BaseFirebaseListener<User?>{
                override fun onLoading(isLoading: Boolean) {
                    if (isPassword){
                        progressSignUpLive.postValue(isLoading)
                    }else{
                        progressSignUpGoogleLive.postValue(isLoading)
                    }
                }
                override fun onSuccess(data: User?) {
                    if (data == null){
                        createUser(user, isPassword)
                    }else{
                        successCreateUser.postValue(data)
                    }
                }
                override fun onFailed(errorMessage: String) {
                    errorSignUpLive.postValue(errorMessage)
                }
            })
        }
    }

    private fun loadFirebaseToken(): String {
        return repository.loadFirebaseToken()
    }
}