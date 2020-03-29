package com.anangkur.mediku.data

import androidx.lifecycle.LiveData
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.model.covid19.Covid19Data

interface DataSource {
    suspend fun getCovid19StatData(): BaseResult<Covid19ApiResponse> { throw Exception() }

    suspend fun insertData(data: List<Covid19Data>) {}
    fun getAllDataByDate(date: String): LiveData<List<Covid19Data>> { throw Exception()}
    fun getAllDataByCountry(country: String): LiveData<List<Covid19Data>> { throw Exception()}
    fun getTopDataByDate(date: String): LiveData<List<Covid19Data>> { throw Exception()}
    fun getDataByCountryAndDate(country: String, date: String): LiveData<List<Covid19Data>> { throw Exception() }
    fun saveCountry(country: String){}
    fun loadCountry(): String { throw Exception() }
}