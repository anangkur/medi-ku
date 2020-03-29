package com.anangkur.mediku.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anangkur.mediku.data.model.covid19.Covid19Data

@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(data: List<Covid19Data>)

    @Query("SELECT * FROM covid19data WHERE date = :date ORDER BY country")
    fun getAllDataByDate(date: String): LiveData<List<Covid19Data>>

    @Query("SELECT * FROM covid19data WHERE country = :country ORDER BY date")
    fun getAllDataByCountry(country: String): LiveData<List<Covid19Data>>

    @Query("SELECT * FROM covid19data WHERE date = :date ORDER BY confirmed DESC LIMIT 10")
    fun getTopDataByDate(date: String): LiveData<List<Covid19Data>>

    @Query("SELECT * FROM covid19data WHERE country = :country AND date = :date LIMIT 1")
    fun getDataByCountryAndDate(country: String, date: String): LiveData<List<Covid19Data>>
}