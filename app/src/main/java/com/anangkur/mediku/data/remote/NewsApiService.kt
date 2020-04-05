package com.anangkur.mediku.data.remote

import com.anangkur.mediku.data.model.news.GetNewsResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getTopHeadlinesNews(
        @Query("apiKey") apiKey: String?,
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("sources") sources: String?,
        @Query("q") q: String?
    ): Response<GetNewsResponse>

    companion object Factory{
        val getApiService: NewsApiService by lazy {

            val mClient =
                OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor { chain ->
                        val request =
                            chain.request()
                                .newBuilder()
                                .addHeader("User-Agent", "UANGKERJA-MOBILE")
                                .build()
                        chain.proceed(request)
                    }
                    .build()

            val mRetrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(mClient)
                .build()

            mRetrofit.create(NewsApiService::class.java)
        }
    }
}