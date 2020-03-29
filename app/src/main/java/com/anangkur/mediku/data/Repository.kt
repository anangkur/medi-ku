package com.anangkur.mediku.data

import androidx.lifecycle.LiveData
import com.anangkur.mediku.base.resultLiveData
import com.anangkur.mediku.data.local.LocalRepository
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.remote.RemoteRepository
import com.anangkur.mediku.util.extractAllData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Repository(val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) {

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

    fun saveCountry(country: String){
        localRepository.saveCountry(country)
    }

    fun loadCountry(): String{
        return localRepository.loadCountry()
    }

    companion object{
        @Volatile private var INSTANCE: Repository? = null
        fun getInstance(remoteRepository: RemoteRepository, localRepository: LocalRepository) = INSTANCE ?: synchronized(
            Repository::class.java){
            INSTANCE ?: Repository(remoteRepository, localRepository).also { INSTANCE = it }
        }
    }
}