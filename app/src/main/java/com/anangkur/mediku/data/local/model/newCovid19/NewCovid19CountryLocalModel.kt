package com.anangkur.mediku.data.local.model.newCovid19


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewCovid19CountryLocalModel(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "",

    @ColumnInfo(name = "Date")
    val date: String? = "",

    @ColumnInfo(name = "Country")
    val country: String? = "",

    @ColumnInfo(name = "Lat")
    val lat: Double? = 0.0,

    @ColumnInfo(name = "Lon")
    val lon: Double? = 0.0,

    @ColumnInfo(name = "Province")
    val province: String? = "",

    @ColumnInfo(name = "Cases")
    val cases: Int? = 0,

    @ColumnInfo(name = "Status")
    val status: String? = ""
)