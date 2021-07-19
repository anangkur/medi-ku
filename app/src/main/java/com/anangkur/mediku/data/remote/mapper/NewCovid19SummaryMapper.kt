package com.anangkur.mediku.data.remote.mapper

import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.data.remote.model.newCovid19.NewCovid19SummaryRemoteModel

class NewCovid19SummaryMapper: RemoteMapper<NewCovid19SummaryRemoteModel, NewCovid19Summary> {

    companion object{
        private var INSTANCE: NewCovid19SummaryMapper? = null
        fun getInstance() = INSTANCE ?: NewCovid19SummaryMapper()
    }

    override fun mapToRemote(data: NewCovid19Summary): NewCovid19SummaryRemoteModel {
        return NewCovid19SummaryRemoteModel(
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

    override fun mapFromRemote(data: NewCovid19SummaryRemoteModel): NewCovid19Summary {
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