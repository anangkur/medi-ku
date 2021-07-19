package com.anangkur.mediku.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anangkur.mediku.data.local.model.newCovid19.NewCovid19CountryLocalModel
import com.anangkur.mediku.data.local.model.newCovid19.NewCovid19SummaryLocalModel
import com.anangkur.mediku.data.local.model.news.ArticleLocalModel

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataSummary(data: List<NewCovid19SummaryLocalModel>)

    @Query("SELECT * FROM newcovid19summarylocalmodel")
    fun getNewCovid19SummaryAll(): LiveData<List<NewCovid19SummaryLocalModel>>

    @Query("SELECT * FROM newcovid19summarylocalmodel ORDER BY TotalConfirmed DESC LIMIT 10")
    fun getNewCovid19SummaryTopCountry(): LiveData<List<NewCovid19SummaryLocalModel>>

    @Query("SELECT * FROM newcovid19summarylocalmodel WHERE Country = :country LIMIT 1")
    fun getNewCovid19SummaryByCountry(country: String): LiveData<List<NewCovid19SummaryLocalModel>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataCountry(data: List<NewCovid19CountryLocalModel>)

    @Query("SELECT * FROM newcovid19countrylocalmodel WHERE Country = :country ORDER BY Date ASC")
    fun getNewCovid19CountryByCountry(country: String): LiveData<List<NewCovid19CountryLocalModel>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataNews(data: List<ArticleLocalModel>)

    @Query("SELECT * FROM ArticleLocalModel WHERE category = :category")
    fun getAllDataByCategory(category: String): LiveData<List<ArticleLocalModel>>
}