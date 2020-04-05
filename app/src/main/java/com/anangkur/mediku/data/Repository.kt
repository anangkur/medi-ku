package com.anangkur.mediku.data

import com.anangkur.mediku.base.resultLiveData
import com.anangkur.mediku.data.local.LocalRepository
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.data.remote.RemoteRepository
import com.anangkur.mediku.util.Const
import com.anangkur.mediku.util.createCompleteData
import com.anangkur.mediku.util.extractAllData

class Repository(val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) {

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
            localRepository.insertDataNews(it.articles.map {article ->
                article.copy(
                    id = "${article.publishedAt}_${article.title}",
                    category = Const.NEWS_HEALTH
                )
            })
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
            localRepository.insertDataNews(it.articles.map {article ->
                article.copy(
                    id = "${article.publishedAt}_${article.title}",
                    category = Const.NEWS_HEALTH,
                    country = country)
            })
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

    companion object{
        @Volatile private var INSTANCE: Repository? = null
        fun getInstance(remoteRepository: RemoteRepository, localRepository: LocalRepository) = INSTANCE ?: synchronized(
            Repository::class.java){
            INSTANCE ?: Repository(remoteRepository, localRepository).also { INSTANCE = it }
        }
    }
}