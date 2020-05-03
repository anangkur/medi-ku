package com.anangkur.mediku.feature.auth.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
            try {
                progressSignUpLive.postValue(true)
                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        progressSignUpLive.postValue(false)
                        if (it.isSuccessful){
                            val profileUpdate = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                            it.result?.user?.updateProfile(profileUpdate)?.addOnCompleteListener {updateProfile ->
                                if (updateProfile.isSuccessful){
                                    createUser(it.result?.user!!, true)
                                }else{
                                    errorSignUpLive.postValue(updateProfile.exception?.message)
                                }
                            }
                        }else{
                            errorSignUpLive.postValue(it.exception?.message)
                        }
                    }
            }catch (e: Exception){
                progressSignUpLive.postValue(false)
                errorSignUpLive.postValue(e.message)
            }
        }
    }

    val progressSignUpGoogleLive = MutableLiveData<Boolean>()
    fun firebaseSignUpWithGoogle(acct: GoogleSignInAccount?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressSignUpGoogleLive.postValue(true)
                val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
                repository.remoteRepository.firebaseAuth.signInWithCredential(credential).addOnCompleteListener{ task ->
                    progressSignUpGoogleLive.postValue(false)
                    if (task.isSuccessful) {
                        createUser(task.result?.user!!, false)
                    } else {
                        errorSignUpLive.postValue(task.exception?.message)
                    }
                }
            }catch (e: Exception){
                progressSignUpGoogleLive.postValue(false)
                errorSignUpLive.postValue(e.message)
            }
        }
    }

    val successCreateUser = MutableLiveData<Boolean>()
    private fun createUser(user: FirebaseUser, isPassword: Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (isPassword){
                    progressSignUpLive.postValue(true)
                }else{
                    progressSignUpGoogleLive.postValue(true)
                }
                repository.remoteRepository.firestore.collection(Const.COLLECTION_USER)
                    .document(user.uid)
                    .get()
                    .addOnSuccessListener {
                        val userFirestore = it.toObject<User>()
                        if (userFirestore != null){
                            successCreateUser.postValue(true)
                        }else{
                            val userMap = User(
                                userId = user.uid,
                                email = user.email?:"",
                                name = user.displayName?:"",
                                height = 0,
                                weight = 0,
                                photo = user.photoUrl.toString(),
                                providerName = user.providerData[user.providerData.size-1].providerId,
                                firebaseToken = loadFirebaseToken()
                            )
                            repository.remoteRepository.firestore.collection(Const.COLLECTION_USER)
                                .document(user.uid)
                                .set(userMap)
                                .addOnSuccessListener {
                                    if (isPassword){
                                        progressSignUpLive.postValue(false)
                                    }else{
                                        progressSignUpGoogleLive.postValue(false)
                                    }
                                    successCreateUser.postValue(true)
                                }
                                .addOnFailureListener { exception ->
                                    if (isPassword){
                                        progressSignUpLive.postValue(false)
                                    }else{
                                        progressSignUpGoogleLive.postValue(false)
                                    }
                                    errorSignUpLive.postValue(exception.message)
                                }
                        }
                    }
                    .addOnFailureListener {
                        if (isPassword){
                            progressSignUpLive.postValue(false)
                        }else{
                            progressSignUpGoogleLive.postValue(false)
                        }
                        errorSignUpLive.postValue(it.message)
                    }
            }catch (e: Exception){
                if (isPassword){
                    progressSignUpLive.postValue(false)
                }else{
                    progressSignUpGoogleLive.postValue(false)
                }
                errorSignUpLive.postValue(e.message)
            }
        }
    }

    private fun loadFirebaseToken(): String {
        return repository.loadFirebaseToken()
    }
}