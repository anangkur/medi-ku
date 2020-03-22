package com.anangkur.mediku.feature.signUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.util.Const
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile.*
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
                errorSignUpLive.postValue(e.message)
            }finally {
                progressSignUpLive.postValue(false)
            }
        }
    }

    val progressSignUpGoogleLive = MutableLiveData<Boolean>()
    fun firebaseSignUpWithGoogle(acct: GoogleSignInAccount?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                progressSignUpGoogleLive.postValue(true)
                val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        createUser(task.result?.user!!, false)
                    } else {
                        errorSignUpLive.postValue(task.exception?.message)
                    }
                }
            }catch (e: Exception){
                errorSignUpLive.postValue(e.message)
            }finally {
                progressSignUpGoogleLive.postValue(false)
            }
        }
    }

    val successCreateUser = MutableLiveData<Void>()
    private fun createUser(user: FirebaseUser, isPassword: Boolean){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (isPassword){
                    progressSignUpLive.postValue(true)
                }else{
                    progressSignUpGoogleLive.postValue(true)
                }
                val userMap = hashMapOf(
                    Const.fieldEmail to user.email,
                    Const.fieldName to user.displayName,
                    Const.fieldPhoto to user.photoUrl.toString(),
                    Const.fieldPhoneNumber to user.phoneNumber
                )
                Firebase.firestore.collection(Const.collectionUser)
                    .document(user.uid)
                    .set(userMap)
                    .addOnSuccessListener { result ->
                        successCreateUser.postValue(result)
                    }
                    .addOnFailureListener { exception ->
                        errorSignUpLive.postValue(exception.message)
                    }
            }catch (e: Exception){
                errorSignUpLive.postValue(e.message)
            }finally {
                if (isPassword){
                    progressSignUpLive.postValue(false)
                }else{
                    progressSignUpGoogleLive.postValue(false)
                }
            }
        }
    }
}