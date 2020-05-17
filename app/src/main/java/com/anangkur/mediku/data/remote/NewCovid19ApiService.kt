package com.anangkur.mediku.data.remote

import com.anangkur.mediku.data.model.newCovid19.NewCovid19DataCountry
import com.anangkur.mediku.data.model.newCovid19.NewCovid19SummaryResponse
import com.anangkur.mediku.util.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface NewCovid19ApiService {

    @GET("summary")
    suspend fun getSummary(): Response<NewCovid19SummaryResponse>

    @GET("country/{country}/status/{status}/live")
    suspend fun getDataCovid19ByCountry(
        @Path("country") country: String,
        @Path("status") status: String
    ): Response<List<NewCovid19DataCountry>>

    companion object Factory{
        val getCovid19ApiService: NewCovid19ApiService by lazy {
            val mClient =
                OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor { chain ->
                        val request = chain.request().newBuilder().build()
                        chain.proceed(request)
                    }
                    .build()

            val mRetrofit = Retrofit.Builder()
                .baseUrl(Const.URL_NEW_COVID19_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build()

            mRetrofit.create(NewCovid19ApiService::class.java)
        }
    }
}