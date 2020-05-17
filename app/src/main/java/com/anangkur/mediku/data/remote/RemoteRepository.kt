package com.anangkur.mediku.data.remote

import com.anangkur.mediku.base.BaseDataSource
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.DataSource
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19SummaryResponse
import com.anangkur.mediku.data.model.news.GetNewsResponse
import com.anangkur.mediku.util.Const
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage

class RemoteRepository(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val storage: FirebaseStorage,
    private val covid19ApiService: Covid19ApiService,
    private val newCovid19ApiService: NewCovid19ApiService
): DataSource, BaseDataSource() {

    /**
     * Firebase
     */
    override suspend fun createUser(user: FirebaseUser, firebaseToken: String, listener: BaseFirebaseListener<User>) {
        try {
            listener.onLoading(true)
            firestore.collection(Const.COLLECTION_USER)
                .document(user.uid)
                .get()
                .addOnSuccessListener {
                    val userFirestore = it.toObject<User>()
                    if (userFirestore != null && it.contains("firebaseToken")){
                        listener.onSuccess(userFirestore)
                    }else{
                        val userMap = User(
                            userId = user.uid,
                            email = user.email?:"",
                            name = user.displayName?:"",
                            height = 0,
                            weight = 0,
                            photo = user.photoUrl.toString(),
                            providerName = user.providerData[user.providerData.size-1].providerId,
                            firebaseToken = firebaseToken
                        )
                        firestore.collection(Const.COLLECTION_USER)
                            .document(userMap.userId)
                            .set(userMap)
                            .addOnSuccessListener { result ->
                                listener.onLoading(false)
                                listener.onSuccess(userMap)
                            }
                            .addOnFailureListener { exception ->
                                listener.onLoading(false)
                                listener.onFailed(exception.message?:"")
                            }
                    }
                }
                .addOnFailureListener {
                    listener.onLoading(false)
                    listener.onFailed(it.message?:"")
                }
        }catch (e: Exception){
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun signInWithGoogle(acct: GoogleSignInAccount?, listener: BaseFirebaseListener<FirebaseUser>) {
        try {
            listener.onLoading(true)
            val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
            firebaseAuth
                .signInWithCredential(credential).addOnCompleteListener{ task ->
                    listener.onLoading(false)
                    if (task.isSuccessful) {
                        listener.onSuccess(task.result?.user!!)
                    } else {
                        listener.onFailed(task.exception?.message?:"")
                    }
                }
        }catch (e: Exception){
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun signInEmail(email: String, password: String, listener: BaseFirebaseListener<FirebaseUser?>) {
        try {
            listener.onLoading(true)
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    listener.onLoading(false)
                    if (it.isSuccessful){
                        listener.onSuccess(it.result?.user)
                    }else{
                        listener.onFailed(it.exception?.message?:"")
                    }
                }
        }catch (e: Exception){
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun signUp(name: String, email: String, password: String, listener: BaseFirebaseListener<FirebaseUser>) {
        try {
            listener.onLoading(true)
            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    listener.onLoading(false)
                    if (it.isSuccessful){
                        val profileUpdate = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        it.result?.user?.updateProfile(profileUpdate)?.addOnCompleteListener {updateProfile ->
                            if (updateProfile.isSuccessful){
                                listener.onSuccess(it.result?.user!!)
                            }else{
                                listener.onFailed(updateProfile.exception?.message?:"")
                            }
                        }
                    }else{
                        listener.onFailed(it.exception?.message?:"")
                    }
                }
        }catch (e: Exception){
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    /**
     * News
     */
    override suspend fun getTopHeadlinesNews(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): BaseResult<GetNewsResponse> {
        return getResult {
            NewsApiService.getApiService.getTopHeadlinesNews(
                apiKey,
                country,
                category,
                sources,
                q
            )
        }
    }

    /**
     * covid 19
     */
    override suspend fun getCovid19StatData(): BaseResult<Covid19ApiResponse> {
        return getResult { covid19ApiService.getCovid19StatData() }
    }

    override suspend fun getDataCovid19ByCountry(
        country: String,
        status: String
    ): BaseResult<List<NewCovid19DataCountry>> {
        return getResult { newCovid19ApiService.getDataCovid19ByCountry(country, status) }
    }

    override suspend fun getSummary(): BaseResult<NewCovid19SummaryResponse> {
        return getResult { newCovid19ApiService.getSummary() }
    }

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance(
            firebaseAuth: FirebaseAuth,
            firestore: FirebaseFirestore,
            storage: FirebaseStorage,
            covid19ApiService: Covid19ApiService,
            newCovid19ApiService: NewCovid19ApiService
        ) = INSTANCE ?: RemoteRepository(firebaseAuth, firestore, storage, covid19ApiService, newCovid19ApiService)
    }
}