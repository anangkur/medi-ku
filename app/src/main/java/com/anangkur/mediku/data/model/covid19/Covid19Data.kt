package com.anangkur.mediku.data.model.covid19

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Covid19Data(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "country")
    var country: String = "",

    @ColumnInfo(name = "confirmed")
    @SerializedName("confirmed")
    val confirmed: Int = 0,

    @ColumnInfo(name = "date")
    @SerializedName("date")
    val date: String = "",

    @ColumnInfo(name = "deaths")
    @SerializedName("deaths")
    val deaths: Int = 0,

    @ColumnInfo(name = "recovered")
    @SerializedName("recovered")
    val recovered: Int = 0
): Parcelable