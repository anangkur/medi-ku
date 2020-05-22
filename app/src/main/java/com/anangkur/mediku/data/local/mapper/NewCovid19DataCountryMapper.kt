package com.anangkur.mediku.data.local.mapper

import com.anangkur.mediku.data.local.model.newCovid19.NewCovid19CountryLocalModel
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Country

class NewCovid19DataCountryMapper: LocalMapper<NewCovid19CountryLocalModel, NewCovid19Country> {

    companion object{
        private var INSTANCE: NewCovid19DataCountryMapper? = null
        fun getInstance() = INSTANCE ?: NewCovid19DataCountryMapper()
    }

    override fun mapToLocal(data: NewCovid19Country): NewCovid19CountryLocalModel {
        return NewCovid19CountryLocalModel(
            id = data.id,
            country = data.country,
            status = data.status,
            date = data.date,
            cases = data.cases,
            lat = data.lat,
            lon = data.lon,
            province = data.province
        )
    }

    override fun mapFromLocal(data: NewCovid19CountryLocalModel): NewCovid19Country {
        return NewCovid19Country(
            id = data.id,
            country = data.country,
            status = data.status,
            date = data.date,
            cases = data.cases,
            lat = data.lat,
            lon = data.lon,
            province = data.province
        )
    }
}