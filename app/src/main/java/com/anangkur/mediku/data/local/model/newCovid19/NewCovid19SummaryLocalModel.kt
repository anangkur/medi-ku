package com.anangkur.mediku.data.local.model.newCovid19

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewCovid19SummaryLocalModel(
    @PrimaryKey
    @ColumnInfo(name = "Country")
    val country: String = "",

    @ColumnInfo(name = "NewConfirmed")
    val newConfirmed: Int? = 0,

    @ColumnInfo(name = "NewDeaths")
    val newDeaths: Int? = 0,

    @ColumnInfo(name = "NewRecovered")
    val newRecovered: Int? = 0,

    @ColumnInfo(name = "Slug")
    val slug: String? = "",

    @ColumnInfo(name = "TotalConfirmed")
    val totalConfirmed: Int? = 0,

    @ColumnInfo(name = "TotalDeaths")
    val totalDeaths: Int? = 0,

    @ColumnInfo(name = "TotalRecovered")
    val totalRecovered: Int? = 0,

    @ColumnInfo(name = "Date")
    var date: String? = ""
)