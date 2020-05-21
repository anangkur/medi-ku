package com.anangkur.mediku.data.remote.service

import com.anangkur.mediku.data.model.covid19.Covid19ApiResponse
import com.anangkur.mediku.util.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


interface Covid19ApiService {

    @GET("covid19/timeseries.json")
    suspend fun getCovid19StatData(): Response<Covid19ApiResponse>

    companion object Factory{
        val getCovid19ApiService: Covid19ApiService by lazy {

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
                .baseUrl(Const.URL_COVID19_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build()

            mRetrofit.create(Covid19ApiService::class.java)
        }
    }
}