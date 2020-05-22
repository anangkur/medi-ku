package com.anangkur.mediku.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.anangkur.mediku.data.DataSource
import com.anangkur.mediku.data.local.mapper.ArticleMapper
import com.anangkur.mediku.data.local.mapper.NewCovid19DataCountryMapper
import com.anangkur.mediku.data.local.mapper.NewCovid19SummaryMapper
import com.anangkur.mediku.data.local.room.AppDao
import com.anangkur.mediku.data.local.room.AppDatabase
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Country
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.util.Const
import com.anangkur.mediku.util.Const.PREF_COUNTRY

class LocalRepository(
    private val articleMapper: ArticleMapper,
    private val newCovid19DataCountryMapper: NewCovid19DataCountryMapper,
    private val newCovid19SummaryMapper: NewCovid19SummaryMapper,
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
    override suspend fun insertDataNews(data: List<Article>) { dao.insertDataNews(data.map { articleMapper.mapToLocal(it) }) }

    override fun getAllDataByCategory(category: String): LiveData<List<Article>> {
        return dao.getAllDataByCategory(category).map { list -> list.map { articleMapper.mapFromLocal(it) } }
    }

    /**
     * new covid 19 summary
     */
    override suspend fun insertDataSummary(data: List<NewCovid19Summary>) {
        dao.insertDataSummary(data.map { newCovid19SummaryMapper.mapToLocal(it) })
    }

    override fun getNewCovid19SummaryAll(): LiveData<List<NewCovid19Summary>> {
        return dao.getNewCovid19SummaryAll().map { list -> list.map { newCovid19SummaryMapper.mapFromLocal(it) } }
    }

    override fun getNewCovid19SummaryTopCountry(): LiveData<List<NewCovid19Summary>> {
        return dao.getNewCovid19SummaryTopCountry().map { list -> list.map { newCovid19SummaryMapper.mapFromLocal(it) } }
    }

    override fun getNewCovid19SummaryByCountry(country: String): LiveData<List<NewCovid19Summary>> {
        return dao.getNewCovid19SummaryByCountry(country).map { list -> list.map { newCovid19SummaryMapper.mapFromLocal(it) } }
    }

    /**
     * new covid 19 country
     */
    override suspend fun insertDataCountry(data: List<NewCovid19Country>) {
        dao.insertDataCountry(data.map { newCovid19DataCountryMapper.mapToLocal(it) })
    }

    override fun getNewCovid19CountryByCountry(country: String): LiveData<List<NewCovid19Country>> {
        return dao.getNewCovid19CountryByCountry(country).map { list -> list.map { newCovid19DataCountryMapper.mapFromLocal(it) } }
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
            NewCovid19DataCountryMapper.getInstance(),
            NewCovid19SummaryMapper.getInstance(),
            context.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE),
            AppDatabase.getDatabase(context).getDao()
        )
    }
}