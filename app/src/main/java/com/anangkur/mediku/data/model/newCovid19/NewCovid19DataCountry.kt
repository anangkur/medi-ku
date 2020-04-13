package com.anangkur.mediku.data.model.newCovid19


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class NewCovid19DataCountry(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: String = "",

    @ColumnInfo(name = "Date")
    @SerializedName("Date")
    val date: String? = "",

    @ColumnInfo(name = "Country")
    @SerializedName("Country")
    val country: String? = "",

    @ColumnInfo(name = "Lat")
    @SerializedName("Lat")
    val lat: Double? = 0.0,

    @ColumnInfo(name = "Lon")
    @SerializedName("Lon")
    val lon: Double? = 0.0,

    @ColumnInfo(name = "Province")
    @SerializedName("Province")
    val province: String? = "",

    @ColumnInfo(name = "Cases")
    @SerializedName("Cases")
    val cases: Int? = 0,

    @ColumnInfo(name = "Status")
    @SerializedName("Status")
    val status: String? = ""
)