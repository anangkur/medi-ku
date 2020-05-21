package com.anangkur.mediku.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.anangkur.mediku.data.DataSource
import com.anangkur.mediku.data.local.mapper.ArticleMapper
import com.anangkur.mediku.data.local.room.AppDao
import com.anangkur.mediku.data.local.room.AppDatabase
import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.util.Const
import com.anangkur.mediku.util.Const.PREF_COUNTRY

class LocalRepository(
    private val mapper: ArticleMapper,
    private val preferences: SharedPreferences,
    private val dao: AppDao
): DataSource {

    override fun saveFirebaseToken(firebaseToken: String) {
        preferences.edit().putString(Const.PREF_FIREBASE_TOKEN, firebaseToken).apply()
    }

    override fun loadFirebaseToken(): String {
        return preferences.getString(Const.PREF_FIREBASE_TOKEN, "") ?: ""
    }

    /**
     * News
     */
    override suspend fun insertDataNews(data: List<Article>) { dao.insertDataNews(data.map { mapper.mapToLocal(it) }) }

    override fun getAllDataByCategory(category: String): LiveData<List<Article>> {
        return dao.getAllDataByCategory(category).map { list -> list.map { mapper.mapFromLocal(it) } }
    }

    /**
     * new covid 19 summary
     */
    override suspend fun insertDataSummary(data: List<NewCovid19Summary>) {
        dao.insertDataSummary(data)
    }

    override fun getNewCovid19SummaryAll(): LiveData<List<NewCovid19Summary>> {
        return dao.getNewCovid19SummaryAll()
    }

    override fun getNewCovid19SummaryTopCountry(): LiveData<List<NewCovid19Summary>> {
        return dao.getNewCovid19SummaryTopCountry()
    }

    override fun getNewCovid19SummaryByCountry(country: String): LiveData<List<NewCovid19Summary>> {
        return dao.getNewCovid19SummaryByCountry(country)
    }

    /**
     * new covid 19 country
     */
    override suspend fun insertDataCountry(data: List<NewCovid19DataCountry>) {
        dao.insertDataCountry(data)
    }

    override fun getNewCovid19CountryByCountry(country: String): LiveData<List<NewCovid19DataCountry>> {
        return dao.getNewCovid19CountryByCountry(country)
    }

    /**
     * preferences
     */
    override fun saveCountry(country: String) {
        preferences.edit().putString(PREF_COUNTRY, country).apply()
    }

    override fun loadCountry(): String {
        return preferences.getString(PREF_COUNTRY, "")?:""
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: LocalRepository? = null
        fun getInstance(context: Context) = INSTANCE ?: LocalRepository(
            ArticleMapper.getInstance(),
            context.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE),
            AppDatabase.getDatabase(context).getDao()
        )
    }
}