package com.anangkur.mediku.data

import android.content.Context
import android.content.SharedPreferences
import com.anangkur.mediku.data.local.LocalRepository
import com.anangkur.mediku.data.remote.RemoteRepository

object Injection {
    fun provideRepository(context: Context, preferences: SharedPreferences) = Repository.getInstance(
        RemoteRepository.getInstance(),
        LocalRepository.getInstance(context, preferences)
    )
}