package com.anangkur.mediku.data

import androidx.lifecycle.LiveData
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.model.covid19.Covid19Data
import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.data.model.newCovid19.NewCovid19SummaryResponse
import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.data.model.news.GetNewsResponse
import retrofit2.Response
import retrofit2.http.Path

interface DataSource {

    fun saveFirebaseToken(firebaseToken: String) { throw Exception() }
    fun loadFirebaseToken(): String { throw Exception() }

    suspend fun getTopHeadlinesNews(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): BaseResult<GetNewsResponse> { throw Exception() }

    suspend fun insertDataNews(data: List<Article>) { throw Exception() }
    fun getAllDataByCategory(category: String): LiveData<List<Article>> { throw Exception() }

    suspend fun getCovid19StatData(): BaseResult<Covid19ApiResponse> { throw Exception() }

    suspend fun getSummary(): BaseResult<NewCovid19SummaryResponse> { throw Exception() }
    suspend fun getDataCovid19ByCountry(country: String, status: String): BaseResult<List<NewCovid19DataCountry>> { throw Exception() }

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

    fun saveCountry(country: String){}
    fun loadCountry(): String { throw Exception() }
}