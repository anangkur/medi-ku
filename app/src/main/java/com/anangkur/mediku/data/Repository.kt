package com.anangkur.mediku.data

import android.net.Uri
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.base.resultLiveData
import com.anangkur.mediku.data.local.LocalRepository
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodMonthly
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.data.remote.RemoteRepository
import com.anangkur.mediku.util.Const
import com.anangkur.mediku.util.createCompleteData
import com.anangkur.mediku.util.extractAllData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import java.util.*

class Repository(val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) {

    /**
     * Firebase
     */
    suspend fun getUser(user: FirebaseUser, listener: BaseFirebaseListener<User?>) {
        remoteRepository.getUser(user, listener)
    }

    suspend fun getUser(listener: BaseFirebaseListener<User?>) {
        remoteRepository.getUser(listener)
    }

    suspend fun createUser(user: FirebaseUser, firebaseToken: String, listener: BaseFirebaseListener<User>){
        remoteRepository.createUser(user, firebaseToken, listener)
    }

    suspend fun signInWithGoogle(acct: GoogleSignInAccount?, listener: BaseFirebaseListener<FirebaseUser>) {
        remoteRepository.signInWithGoogle(acct, listener)
    }

    suspend fun signInWithEmail(email: String, password: String, listener: BaseFirebaseListener<FirebaseUser?>) {
        remoteRepository.signInEmail(email, password, listener)
    }

    suspend fun signUp(name: String, email: String, password: String, listener: BaseFirebaseListener<FirebaseUser>){
        remoteRepository.signUp(name, email, password, listener)
    }

    suspend fun resetPassword(email: String, listener: BaseFirebaseListener<String>) {
        remoteRepository.resetPassword(email, listener)
    }

    suspend fun editPassword(newPassword: String, listener: BaseFirebaseListener<Boolean>) {
        remoteRepository.editPassword(newPassword, listener)
    }

    suspend fun reAuthenticate(oldPassword: String, listener: BaseFirebaseListener<Boolean>) {
        remoteRepository.reAuthenticate(oldPassword, listener)
    }

    suspend fun logout(listener: BaseFirebaseListener<Boolean>) {
        remoteRepository.logout(listener)
    }

    suspend fun getMedicalRecords(listener: BaseFirebaseListener<List<MedicalRecord>>) {
        remoteRepository.getMedicalRecords(listener)
    }

    suspend fun addMedicalRecord(medicalRecord: MedicalRecord, listener: BaseFirebaseListener<MedicalRecord>) {
        remoteRepository.addMedicalRecord(medicalRecord, listener)
    }

    suspend fun uploadDocument(document: Uri, listener: BaseFirebaseListener<Uri>) {
        remoteRepository.uploadDocument(document, listener)
    }

    suspend fun getMenstrualPeriod(year: String, listener: BaseFirebaseListener<MenstrualPeriodMonthly>) {
        remoteRepository.getMenstrualPeriod(year, listener)
    }

    suspend fun addMenstrualPeriod(menstrualPeriodResume: MenstrualPeriodResume, listener: BaseFirebaseListener<MenstrualPeriodResume>) {
        remoteRepository.addMenstrualPeriod(menstrualPeriodResume, listener)
    }

    suspend fun editProfile(user: User, listener: BaseFirebaseListener<User>) {
        remoteRepository.editProfile(user, listener)
    }

    suspend fun uploadImage(image: Uri, listener: BaseFirebaseListener<Uri>) {
        remoteRepository.uploadImage(image, listener)
    }

    suspend fun checkUserLogin(listener: BaseFirebaseListener<Boolean>){
        remoteRepository.checkUserLogin(listener)
    }

    /**
     * covid 19 data
     */
    fun getCovid19StatData(date: String) = resultLiveData(
        databaseQuery = { localRepository.getAllDataByDate(date) },
        networkCall = { remoteRepository.getCovid19StatData() },
        saveCallResult = { localRepository.insertData(it.extractAllData()) }
    )

    fun getAllDataByDate(date: String) = resultLiveData(
        databaseQuery = { localRepository.getAllDataByDate(date) }
    )

    fun getAllDataByCountry(country: String) = resultLiveData(
        databaseQuery = { localRepository.getAllDataByCountry(country) }
    )

    fun getTopDataByDate(date: String) = resultLiveData(
        databaseQuery = { localRepository.getTopDataByDate(date) }
    )

    fun getDataByCountryAndDate(country: String, date: String) = resultLiveData(
        databaseQuery = { localRepository.getDataByCountryAndDate(country, date) }
    )

    /**
     * Covid 19 new Data
     */
    fun getNewCovid19Summary() = resultLiveData(
        databaseQuery = { localRepository.getNewCovid19SummaryAll() },
        networkCall = { remoteRepository.getSummary() },
        saveCallResult = { localRepository.insertDataSummary(it.createCompleteData()) }
    )

    fun getNewCovid19SummaryTopCountry() = resultLiveData(
        databaseQuery = { localRepository.getNewCovid19SummaryTopCountry() }
    )

    fun getNewCovid19SummaryByCountry(country: String) = resultLiveData(
        databaseQuery = { localRepository.getNewCovid19SummaryByCountry(country) }
    )

    fun getNewCovid19Country(country: String, status: String) = resultLiveData(
        databaseQuery = { localRepository.getNewCovid19CountryByCountry(country) },
        networkCall = { remoteRepository.getDataCovid19ByCountry(country, status) },
        saveCallResult = { localRepository.insertDataCountry(it.createCompleteData()) }
    )

    /**
     * News
     */

    fun getHealthNews() = resultLiveData(
        databaseQuery = { localRepository.getAllDataByCategory(Const.NEWS_HEALTH) },
        networkCall = { remoteRepository.getTopHeadlinesNews(
            Const.API_KEY,
            null,
            Const.NEWS_HEALTH,
            null,
            null)
        },
        saveCallResult = {
            it?.let { localRepository.insertDataNews(it.map {article ->
                article.apply {
                    this.id = "${article.publishedAt}_${article.title}"
                    this.category = Const.NEWS_HEALTH
                }
            }) }
        }
    )

    fun getHealthNewsByCountry(country: String) = resultLiveData(
        databaseQuery = { localRepository.getAllDataByCategory(Const.NEWS_HEALTH) },
        networkCall = { remoteRepository.getTopHeadlinesNews(
            Const.API_KEY,
            country,
            Const.NEWS_HEALTH,
            null,
            null)
        },
        saveCallResult = {
            it?.let { localRepository.insertDataNews(it.map {article ->
                article.apply {
                    this.id = "${article.publishedAt}_${article.title}"
                    this.category = Const.NEWS_HEALTH
                    this.country = country
                }
            }) }
        }
    )

    /**
     * preferences
     */
    fun saveCountry(country: String){
        localRepository.saveCountry(country)
    }

    fun loadCountry(): String{
        return localRepository.loadCountry()
    }

    fun saveFirebaseToken(firebaseToken: String){
        localRepository.saveFirebaseToken(firebaseToken)
    }

    fun loadFirebaseToken(): String {
        return localRepository.loadFirebaseToken()
    }

    companion object{
        @Volatile private var INSTANCE: Repository? = null
        fun getInstance(remoteRepository: RemoteRepository, localRepository: LocalRepository) = INSTANCE ?: synchronized(
            Repository::class.java){
            INSTANCE ?: Repository(remoteRepository, localRepository).also { INSTANCE = it }
        }
    }
}