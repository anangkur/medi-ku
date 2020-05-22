package com.anangkur.mediku.feature.mapper

import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.feature.model.newCovid19.NewCovid19SummaryIntent

class NewCovid19SummaryMapper: IntentMapper<NewCovid19SummaryIntent, NewCovid19Summary> {

    companion object{
        private var INSTANCE: NewCovid19SummaryMapper? = null
        fun getInstance() = INSTANCE ?: NewCovid19SummaryMapper()
    }

    override fun mapToIntent(data: NewCovid19Summary): NewCovid19SummaryIntent {
        return NewCovid19SummaryIntent(
            country = data.country,
            newConfirmed = data.newConfirmed,
            newDeaths = data.newDeaths,
            newRecovered = data.newRecovered,
            slug = data.slug,
            totalConfirmed = data.totalConfirmed,
            totalDeaths = data.totalDeaths,
            totalRecovered = data.totalRecovered
        )
    }

    override fun mapFromIntent(data: NewCovid19SummaryIntent): NewCovid19Summary {
        return NewCovid19Summary(
            country = data.country,
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