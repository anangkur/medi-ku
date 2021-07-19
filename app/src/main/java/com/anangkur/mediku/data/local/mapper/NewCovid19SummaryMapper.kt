package com.anangkur.mediku.data.local.mapper

import com.anangkur.mediku.data.local.model.newCovid19.NewCovid19SummaryLocalModel
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary

class NewCovid19SummaryMapper: LocalMapper<NewCovid19SummaryLocalModel, NewCovid19Summary> {

    companion object{
        private var INSTANCE: NewCovid19SummaryMapper? = null
        fun getInstance() = INSTANCE ?: NewCovid19SummaryMapper()
    }

    override fun mapToLocal(data: NewCovid19Summary): NewCovid19SummaryLocalModel {
        return NewCovid19SummaryLocalModel(
            country = data.country,
            date = data.date,
            newConfirmed = data.newConfirmed,
            newDeaths = data.newDeaths,
            newRecovered = data.newRecovered,
            slug = data.slug,
            totalConfirmed = data.totalConfirmed,
            totalDeaths = data.totalDeaths,
            totalRecovered = data.totalRecovered
        )
    }

    override fun mapFromLocal(data: NewCovid19SummaryLocalModel): NewCovid19Summary {
        return NewCovid19Summary(
            country = data.country,
            date = data.date,
            newConfirmed = data.newConfirmed,
            newDeaths = data.newDeaths,
            newRecovered = data.newRecovered,
            slug = data.slug,
            totalConfirmed = data.totalConfirmed,
            totalDeaths = data.totalDeaths,
            totalRecovered = data.totalRecovered
        )
    }
}