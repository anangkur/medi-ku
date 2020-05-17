package com.anangkur.mediku.data

import androidx.lifecycle.LiveData
import com.anangkur.mediku.base.BaseFirebaseListener
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.model.covid19.Covid19Data
import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.data.model.newCovid19.NewCovid19SummaryResponse
import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.data.model.news.GetNewsResponse
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface DataSource {

    /**
     * Firebase
     */
    suspend fun createUser(user: FirebaseUser, firebaseToken: String, listener: BaseFirebaseListener<User>) {}
    suspend fun signInWithGoogle(acct: GoogleSignInAccount?, listener: BaseFirebaseListener<FirebaseUser>) {}
    suspend fun signInEmail(email: String, password: String, listener: BaseFirebaseListener<FirebaseUser?>) {}

    /**
     * Preferences
     */
    fun saveFirebaseToken(firebaseToken: String) { throw Exception() }
    fun loadFirebaseToken(): String { throw Exception() }
    fun saveCountry(country: String){}
    fun loadCountry(): String { throw Exception() }

    /**
     * Room
     */
    suspend fun insertDataNews(data: List<Article>) { throw Exception() }
    fun getAllDataByCategory(category: String): LiveData<List<Article>> { throw Exception() }

    suspend fun insertData(data: List<Covid19Data>) {}
    fun getAllDataByDate(date: String): LiveData<List<Covid19Data>> { throw Exception()}
    fun getAllDataByCountry(country: String): LiveData<List<Covid19Data>> { throw Exception()}
    fun getTopDataByDate(date: String): LiveData<List<Covid19Data>> { throw Exception()}
    fun getDataByCountryAndDate(country: String, date: String): LiveData<List<Covid19Data>> { throw Exception() }

    suspend fun insertDataSummary(data: List<NewCovid19Summary>) {}
    fun getNewCovid19SummaryAll(): LiveData<List<NewCovid19Summary>> { throw Exception() }
    fun getNewCovid19SummaryTopCountry(): LiveData<List<NewCovid19Summary>> { throw Exception() }
    fun getNewCovid19SummaryByCountry(country: String): LiveData<List<NewCovid19Summary>> { throw Exception() }

    suspend fun insertDataCountry(data: List<NewCovid19DataCountry>) {}
    fun getNewCovid19CountryByCountry(country: String): LiveData<List<NewCovid19DataCountry>> { throw Exception() }

    /**
     * Retrofit
     */
    suspend fun getTopHeadlinesNews(apiKey: String?, country: String?, category: String?, sources: String?, q: String?): BaseResult<GetNewsResponse> { throw Exception() }

    suspend fun getCovid19StatData(): BaseResult<Covid19ApiResponse> { throw Exception() }

    suspend fun getSummary(): BaseResult<NewCovid19SummaryResponse> { throw Exception() }
    suspend fun getDataCovid19ByCountry(country: String, status: String): BaseResult<List<NewCovid19DataCountry>> { throw Exception() }
}