package com.anangkur.mediku.feature.covid19

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.model.covid19.Covid19Data

class CovidViewModel(private val repository: Repository): ViewModel(){

    val country = repository.loadCountry()

    private val triggerGetCovid19Data = MutableLiveData<String>()
    val covidLiveData = triggerGetCovid19Data.switchMap {
        repository.getCovid19StatData(it)
    }
    fun getCovid19Data(date: String){
        triggerGetCovid19Data.postValue(date)
    }

    fun getStatCovid(listCovid19Data: List<Covid19Data>): Triple<Int, Int, Int>{
        var confirmed = 0
        var death = 0
        var recovered = 0
        for (covid19Data in listCovid19Data){
            confirmed += covid19Data.confirmed
            death += covid19Data.deaths
            recovered += covid19Data.recovered
        }
        return Triple(confirmed, death, recovered)
    }

    private val triggerGetCovid19DataOnYourCountry = MutableLiveData<Pair<String, String>>()
    val covid19DataOnYourCountryLive = triggerGetCovid19DataOnYourCountry.switchMap {
        repository.getDataByCountryAndDate(it.first, it.second)
    }
    fun getCovid19DataOnYourCountry(country: String, date: String){
        triggerGetCovid19DataOnYourCountry.postValue(Pair(country, date))
    }

    private val triggerGetCovid19DataTopCountry = MutableLiveData<String>()
    val covid19DataTopCountry = triggerGetCovid19DataTopCountry.switchMap {
        repository.getTopDataByDate(it)
    }
    fun getCovid19DataTopCountry(date: String){
        triggerGetCovid19DataTopCountry.postValue(date)
    }
}