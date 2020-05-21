package com.anangkur.mediku.data.remote

import android.net.Uri
import com.anangkur.mediku.base.BaseDataSource
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.DataSource
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodMonthly
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19SummaryResponse
import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.data.remote.mapper.ArticleMapper
import com.anangkur.mediku.data.remote.service.NewCovid19ApiService
import com.anangkur.mediku.data.remote.service.NewsApiService
import com.anangkur.mediku.util.Const
import com.anangkur.mediku.util.getCurrentTimeStamp
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlin.collections.ArrayList

class RemoteRepository(
    private val mapper: ArticleMapper,
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage,
    private val newCovid19ApiService: NewCovid19ApiService
): DataSource, BaseDataSource() {

    override suspend fun getUser(user: FirebaseUser, listener: BaseFirebaseListener<User?>) {
        try {
            listener.onLoading(true)
            firestore.collection(Const.COLLECTION_USER)
                .document(user.uid)
                .get()
                .addOnSuccessListener {
                    listener.onLoading(false)
                    val userFirestore = it.toObject<User>()
                    if (userFirestore != null && it.contains("firebaseToken")){
                        listener.onSuccess(userFirestore)
                    }else{
                        listener.onSuccess(null)
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(it.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun getUser(listener: BaseFirebaseListener<User?>) {
        try {
            val user = firebaseAuth.currentUser!!
            listener.onLoading(true)
            firestore.collection(Const.COLLECTION_USER)
                .document(user.uid)
                .get()
                .addOnSuccessListener {
                    listener.onLoading(false)
                    val userFirestore = it.toObject<User>()
                    if (userFirestore != null && it.contains("firebaseToken")){
                        listener.onSuccess(userFirestore)
                    }else{
                        listener.onSuccess(null)
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(it.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun createUser(user: FirebaseUser, firebaseToken: String, listener: BaseFirebaseListener<User>) {
        try {
            listener.onLoading(true)
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
                    exception.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(exception.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun signInWithGoogle(acct: GoogleSignInAccount?, listener: BaseFirebaseListener<FirebaseUser>) {
        try {
            listener.onLoading(true)
            val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener{ task ->
                    listener.onLoading(false)
                    if (task.isSuccessful) {
                        listener.onSuccess(task.result?.user!!)
                    } else {
                        task.exception?.printStackTrace()
                        listener.onFailed(task.exception?.message?:"")
                    }
                }
        }catch (e: Exception){
            e.printStackTrace()
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
                        it.exception?.printStackTrace()
                        listener.onFailed(it.exception?.message?:"")
                    }
                }
        }catch (e: Exception){
            e.printStackTrace()
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
                        it.exception?.printStackTrace()
                        listener.onFailed(it.exception?.message?:"")
                    }
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun resetPassword(email: String, listener: BaseFirebaseListener<String>) {
        try {
            listener.onLoading(true)
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    listener.onLoading(false)
                    if (it.isSuccessful){
                        listener.onSuccess("Email sent!")
                    }else{
                        it.exception?.printStackTrace()
                        listener.onFailed(it.exception?.message?:"")
                    }
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun editPassword(newPassword: String, listener: BaseFirebaseListener<Boolean>) {
        try {
            listener.onLoading(true)
            val user = firebaseAuth.currentUser
            user?.updatePassword(newPassword)?.addOnCompleteListener {task ->
                listener.onLoading(false)
                if (task.isSuccessful){
                    listener.onSuccess(true)
                }else{
                    task.exception?.printStackTrace()
                    listener.onFailed(task.exception?.message?:"")
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun reAuthenticate(oldPassword: String, listener: BaseFirebaseListener<Boolean>) {
        try {
            listener.onLoading(true)
            val user = firebaseAuth.currentUser
            val credential = EmailAuthProvider.getCredential(user?.email?:"", oldPassword)
            user?.reauthenticate(credential)?.addOnCompleteListener {
                listener.onLoading(false)
                if (it.isSuccessful){
                    listener.onSuccess(true)
                }else{
                    it.exception?.printStackTrace()
                    listener.onFailed(it.exception?.message?:"")
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun logout(listener: BaseFirebaseListener<Boolean>) {
        try {
            listener.onLoading(true)
            firebaseAuth.signOut()
            listener.onLoading(false)
            listener.onSuccess(true)
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun getMedicalRecords(listener: BaseFirebaseListener<List<MedicalRecord>>) {
        try {
            listener.onLoading(true)
            val user = firebaseAuth.currentUser
            firestore
                .collection(Const.COLLECTION_MEDICAL_RECORD)
                .document(user?.uid?:"")
                .collection(Const.COLLECTION_MEDICAL_RECORD)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {result ->
                    listener.onLoading(false)
                    val listData = ArrayList<MedicalRecord>()
                    for (querySnapshot in result){
                        val data = querySnapshot.toObject<MedicalRecord>()
                        if (data != null){
                            listData.add(data)
                        }
                    }
                    if (listData.isEmpty()){
                        listener.onFailed("You don't have any medical record.")
                    }else{
                        listener.onSuccess(listData)
                    }
                }
                .addOnFailureListener {exception ->
                    exception.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(exception.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun addMedicalRecord(medicalRecord: MedicalRecord, listener: BaseFirebaseListener<MedicalRecord>) {
        try {
            listener.onLoading(true)
            val user = firebaseAuth.currentUser
            firestore.collection(Const.COLLECTION_MEDICAL_RECORD)
                .document(user?.uid?:"")
                .collection(Const.COLLECTION_MEDICAL_RECORD)
                .document(medicalRecord.createdAt)
                .set(medicalRecord)
                .addOnSuccessListener {
                    listener.onLoading(false)
                    listener.onSuccess(medicalRecord)
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(exception.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun uploadDocument(document: Uri, listener: BaseFirebaseListener<Uri>) {
        try {
            listener.onLoading(true)
            val fileName = document.lastPathSegment?:""
            val extension = fileName.substring(fileName.lastIndexOf("."))
            val user = firebaseAuth.currentUser
            val storageReference = storage.reference
                .child(Const.COLLECTION_MEDICAL_RECORD)
                .child(user?.uid?:"")
                .child("${user?.uid}_${getCurrentTimeStamp() ?:"1990-01-01 00:00:00"}$extension")
            storageReference.putFile(document)
                .addOnProgressListener {
                    listener.onLoading(true)
                }.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        throw task.exception!!
                    }
                    storageReference.downloadUrl
                }.addOnSuccessListener {
                    listener.onLoading(false)
                    listener.onSuccess(it)
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(it.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun getMenstrualPeriod(year: String, listener: BaseFirebaseListener<MenstrualPeriodMonthly>) {
        try {
            listener.onLoading(true)
            val user = firebaseAuth.currentUser
            firestore
                .collection(Const.COLLECTION_MENSTRUAL_PERIOD)
                .document(user?.uid?:"")
                .collection(year)
                .get()
                .addOnSuccessListener {result ->
                    val menstrualPeriodMonthly = MenstrualPeriodMonthly()
                    if (!result.isEmpty){
                        for (querySnapshot in result){
                            val data = querySnapshot?.toObject<MenstrualPeriodResume>()
                            when (querySnapshot.id){
                                Const.KEY_JAN -> { menstrualPeriodMonthly.jan = data }
                                Const.KEY_FEB -> { menstrualPeriodMonthly.feb = data }
                                Const.KEY_MAR -> { menstrualPeriodMonthly.mar = data }
                                Const.KEY_APR -> { menstrualPeriodMonthly.apr = data }
                                Const.KEY_MAY -> { menstrualPeriodMonthly.may = data }
                                Const.KEY_JUN -> { menstrualPeriodMonthly.jun = data }
                                Const.KEY_JUL -> { menstrualPeriodMonthly.jul = data }
                                Const.KEY_AUG -> { menstrualPeriodMonthly.aug = data }
                                Const.KEY_SEP -> { menstrualPeriodMonthly.sep = data }
                                Const.KEY_OCT -> { menstrualPeriodMonthly.oct = data }
                                Const.KEY_NOV -> { menstrualPeriodMonthly.nov = data }
                                Const.KEY_DEC -> { menstrualPeriodMonthly.dec = data }
                            }
                        }
                    }
                    listener.onLoading(false)
                    listener.onSuccess(menstrualPeriodMonthly)
                }
                .addOnFailureListener {exception ->
                    exception.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(exception.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun addMenstrualPeriod(menstrualPeriodResume: MenstrualPeriodResume, listener: BaseFirebaseListener<MenstrualPeriodResume>) {
        try {
            listener.onLoading(true)
            val user = firebaseAuth.currentUser
            firestore.collection(Const.COLLECTION_MENSTRUAL_PERIOD)
                .document(user?.uid?:"")
                .collection(menstrualPeriodResume.year)
                .document(menstrualPeriodResume.month)
                .set(menstrualPeriodResume)
                .addOnSuccessListener {
                    listener.onLoading(false)
                    listener.onSuccess(menstrualPeriodResume)
                }
                .addOnFailureListener { exception ->
                    exception.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(exception.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun editProfile(user: User, listener: BaseFirebaseListener<User>) {
        try {
            listener.onLoading(true)
            val userFirebase = firebaseAuth.currentUser
            firestore
                .collection(Const.COLLECTION_USER)
                .document(userFirebase?.uid?:"")
                .set(user)
                .addOnSuccessListener { result ->
                    listener.onLoading(false)
                    listener.onSuccess(user)
                }
                .addOnFailureListener { exeption ->
                    exeption.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(exeption.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun uploadImage(image: Uri, listener: BaseFirebaseListener<Uri>) {
        try {
            listener.onLoading(true)
            val fileName = image.lastPathSegment?:""
            val extension = fileName.substring(fileName.lastIndexOf("."))
            val user = firebaseAuth.currentUser
            val storageReference = storage.reference
                .child(Const.STORAGE_PROFILE_PHOTO)
                .child(user?.uid?:"")
                .child("${user?.uid}$extension")
            storageReference.putFile(image)
                .addOnProgressListener {
                    listener.onLoading(true)
                }.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        throw task.exception!!
                    }
                    storageReference.downloadUrl
                }.addOnSuccessListener {
                    listener.onLoading(false)
                    listener.onSuccess(it)
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    listener.onLoading(false)
                    listener.onFailed(it.message?:"")
                }
        }catch (e: Exception){
            e.printStackTrace()
            listener.onLoading(false)
            listener.onFailed(e.message?:"")
        }
    }

    override suspend fun checkUserLogin(listener: BaseFirebaseListener<Boolean>) {
        try {
            listener.onLoading(true)
            val user = firebaseAuth.currentUser
            if (user == null){
                listener.onSuccess(false)
            }else{
                listener.onSuccess(true)
            }
        }catch (e: Exception){
            e.printStackTrace()
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
    ): BaseResult<List<Article>?> {
        val baseResult = getResult {
            NewsApiService.getApiService.getTopHeadlinesNews(
                apiKey,
                country,
                category,
                sources,
                q
            )
        }
        return if (baseResult.status == BaseResult.Status.SUCCESS){
            BaseResult.success(baseResult.data?.articles?.map { mapper.mapFromRemote(it) })
        }else{
            BaseResult.error(baseResult.message?:"")
        }
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
        fun getInstance() = INSTANCE ?: RemoteRepository(
            ArticleMapper.getInstance(),
            FirebaseAuth.getInstance(),
            Firebase.firestore,
            FirebaseStorage.getInstance(),
            NewCovid19ApiService.getCovid19ApiService
        )
    }
}