package com.anangkur.mediku.feature.signIn

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.util.Const
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(private val repository: Repository): ViewModel() {

    val progressSignInLive = MutableLiveData<Boolean>()
    val resultSignInLive = MutableLiveData<FirebaseUser>()
    val errorSignInLive = MutableLiveData<String>()
    fun firebaseSignIn(email: String, password: String){
        CoroutineScope(Dispatchers.IO).launch{
            try {
                progressSignInLive.postValue(true)
                repository.remoteRepository.firebaseAuth
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        progressSignInLive.postValue(false)
                        if (it.isSuccessful){
                            resultSignInLive.postValue(it.result?.user)
                        }else{
                            errorSignInLive.postValue(it.exception?.message)
                        }
                    }
            }catch (e: Exception){
                progressSignInLive.postValue(false)
                errorSignInLive.postValue(e.message)
            }
        }
    }

    val progressSignInGoogleLive = MutableLiveData<Boolean>()
    fun firebaseSignInWithGoogle(acct: GoogleSignInAccount?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressSignInGoogleLive.postValue(true)
                val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
                repository.remoteRepository.firebaseAuth
                    .signInWithCredential(credential).addOnCompleteListener{ task ->
                        progressSignInGoogleLive.postValue(false)
                        if (task.isSuccessful) {
                            createUser(task.result?.user!!)
                        } else {
                            errorSignInLive.postValue(task.exception?.message)
                        }
                    }
            }catch (e: Exception){
                progressSignInGoogleLive.postValue(false)
                errorSignInLive.postValue(e.message)
            }
        }
    }

    val successCreateUser = MutableLiveData<Void>()
    private fun createUser(user: FirebaseUser){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressSignInGoogleLive.postValue(true)
                repository.remoteRepository.firestore.collection(Const.COLLECTION_USER)
                    .document(user.uid)
                    .get()
                    .addOnSuccessListener {
                        val userFirestore = it.toObject<User>()
                        if (userFirestore != null){
                            resultSignInLive.postValue(user)
                        }else{
                            val userMap = User(
                                userId = user.uid,
                                email = user.email?:"",
                                name = user.displayName?:"",
                                height = 0,
                                weight = 0,
                                photo = user.photoUrl.toString(),
                                providerName = user.providerData[user.providerData.size-1].providerId)
                            repository.remoteRepository.firestore.collection(Const.COLLECTION_USER)
                                .document(user.uid)
                                .set(userMap)
                                .addOnSuccessListener { result ->
                                    progressSignInGoogleLive.postValue(false)
                                    successCreateUser.postValue(result)
                                }
                                .addOnFailureListener { exception ->
                                    progressSignInGoogleLive.postValue(false)
                                    errorSignInLive.postValue(exception.message)
                                }
                        }
                    }
                    .addOnFailureListener {
                        progressSignInGoogleLive.postValue(false)
                        errorSignInLive.postValue(it.message)
                    }
            }catch (e: Exception){
                progressSignInGoogleLive.postValue(false)
                errorSignInLive.postValue(e.message)
            }
        }
    }
}