package com.anangkur.mediku.data.remote

import com.anangkur.mediku.base.BaseDataSource
import com.anangkur.mediku.data.DataSource
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19SummaryResponse
import com.anangkur.mediku.data.model.news.GetNewsResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import retrofit2.Response


class RemoteRepository(
    val firebaseAuth: FirebaseAuth,
    val firestore: FirebaseFirestore,
    val storage: FirebaseStorage,
    private val covid19ApiService: Covid19ApiService,
    private val newCovid19ApiService: NewCovid19ApiService
): DataSource, BaseDataSource() {

    override suspend fun getTopHeadlinesNews(
        apiKey: String?,
        country: String?,
        category: String?,
        sources: String?,
        q: String?
    ): BaseResult<GetNewsResponse> {
        return getResult {
            NewsApiService.getApiService.getTopHeadlinesNews(
                apiKey,
                country,
                category,
                sources,
                q
            )
        }
    }

    override suspend fun getCovid19StatData(): BaseResult<Covid19ApiResponse> {
        return getResult { covid19ApiService.getCovid19StatData() }
    }

    override suspend fun getDataCovid19ByCountry(
        country: String,
        status: String
    ): BaseResult<List<NewCovid19DataCountry>> {
        return getResult { newCovid19ApiService.getDataCovid19ByCountry(country, status) }
    }

    override suspend fun getSummary(): BaseResult<NewCovid19SummaryResponse> {
        return getResult { newCovid19ApiService.getSummary() }
    }

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance(
            firebaseAuth: FirebaseAuth,
            firestore: FirebaseFirestore,
            storage: FirebaseStorage,
            covid19ApiService: Covid19ApiService,
            newCovid19ApiService: NewCovid19ApiService
        ) = INSTANCE ?: RemoteRepository(firebaseAuth, firestore, storage, covid19ApiService, newCovid19ApiService)
    }
}