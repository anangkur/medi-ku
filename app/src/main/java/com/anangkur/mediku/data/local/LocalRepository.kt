package com.anangkur.mediku.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.anangkur.mediku.data.DataSource
import com.anangkur.mediku.data.local.room.AppDao
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.model.covid19.Covid19Data
import com.anangkur.mediku.util.Const.EXTRA_COUNTRY

class LocalRepository(
    private val context: Context,
    private val preferences: SharedPreferences,
    private val dao: AppDao
): DataSource {

    override suspend fun insertData(data: List<Covid19Data>) {
        dao.insertData(data)
    }

    override fun getAllDataByDate(date: String): LiveData<List<Covid19Data>> {
        return dao.getAllDataByDate(date)
    }

    override fun getAllDataByCountry(country: String): LiveData<List<Covid19Data>> {
        return dao.getAllDataByCountry(country)
    }

    override fun getTopDataByDate(date: String): LiveData<List<Covid19Data>> {
        return dao.getTopDataByDate(date)
    }

    override fun getDataByCountryAndDate(
        country: String,
        date: String
    ): LiveData<List<Covid19Data>> {
        return dao.getDataByCountryAndDate(country, date)
    }

    override fun saveCountry(country: String) {
        preferences.edit().putString(EXTRA_COUNTRY, country).apply()
    }

    override fun loadCountry(): String {
        return preferences.getString(EXTRA_COUNTRY, "")?:""
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: LocalRepository? = null
        fun getInstance(
            context: Context,
            preferences: SharedPreferences,
            dao: AppDao
        ) = INSTANCE ?: LocalRepository(context, preferences, dao)
    }
}