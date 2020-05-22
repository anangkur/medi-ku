package com.anangkur.mediku.feature.view.covid.covid19

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.anangkur.mediku.data.Repository
import com.anangkur.mediku.feature.model.newCovid19.NewCovid19SummaryIntent

class CovidViewModel(private val repository: Repository): ViewModel(){

    val country = repository.loadCountry()

    private val triggerGetCovid19Data = MutableLiveData<Boolean>()
    val covidLiveData = triggerGetCovid19Data.switchMap {
        repository.getNewCovid19Summary()
    }
    fun getCovid19Data(){
        triggerGetCovid19Data.postValue(true)
    }

    fun getStatCovid(listCovid19Data: List<NewCovid19SummaryIntent>): Triple<Int, Int, Int>{
        var confirmed = 0
        var death = 0
        var recovered = 0
        for (covid19Data in listCovid19Data){
            confirmed += covid19Data.totalConfirmed?:0
            death += covid19Data.totalDeaths?:0
            recovered += covid19Data.totalRecovered?:0
        }
        return Triple(confirmed, death, recovered)
    }

    private val triggerGetCovid19DataOnYourCountry = MutableLiveData<String>()
    val covid19DataOnYourCountryLive = triggerGetCovid19DataOnYourCountry.switchMap {
        repository.getNewCovid19SummaryByCountry(it)
    }
    fun getCovid19DataOnYourCountry(country: String){
        triggerGetCovid19DataOnYourCountry.postValue(country)
    }

    private val triggerGetCovid19DataTopCountry = MutableLiveData<Boolean>()
    val covid19DataTopCountry = triggerGetCovid19DataTopCountry.switchMap {
        repository.getNewCovid19SummaryTopCountry()
    }
    fun getCovid19DataTopCountry(){
        triggerGetCovid19DataTopCountry.postValue(true)
    }
}