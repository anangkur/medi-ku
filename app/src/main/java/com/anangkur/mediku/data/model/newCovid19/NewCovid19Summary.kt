package com.anangkur.mediku.data.model.newCovid19

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class NewCovid19Summary(
    @PrimaryKey
    @ColumnInfo(name = "Country")
    @SerializedName("Country")
    val country: String = "",

    @ColumnInfo(name = "NewConfirmed")
    @SerializedName("NewConfirmed")
    val newConfirmed: Int? = 0,

    @ColumnInfo(name = "NewDeaths")
    @SerializedName("NewDeaths")
    val newDeaths: Int? = 0,

    @ColumnInfo(name = "NewRecovered")
    @SerializedName("NewRecovered")
    val newRecovered: Int? = 0,

    @ColumnInfo(name = "Slug")
    @SerializedName("Slug")
    val slug: String? = "",

    @ColumnInfo(name = "TotalConfirmed")
    @SerializedName("TotalConfirmed")
    val totalConfirmed: Int? = 0,

    @ColumnInfo(name = "TotalDeaths")
    @SerializedName("TotalDeaths")
    val totalDeaths: Int? = 0,

    @ColumnInfo(name = "TotalRecovered")
    @SerializedName("TotalRecovered")
    val totalRecovered: Int? = 0,

    @ColumnInfo(name = "Date")
    var date: String? = ""
): Parcelable