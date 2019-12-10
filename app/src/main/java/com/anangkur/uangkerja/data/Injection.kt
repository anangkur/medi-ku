package com.anangkur.uangkerja.data

import android.content.Context
import android.content.SharedPreferences
import com.anangkur.uangkerja.data.local.LocalRepository
import com.anangkur.uangkerja.data.remote.RemoteRepository

object Injection {
    fun provideRepository(context: Context, preferences: SharedPreferences) = Repository.getInstance(
        RemoteRepository.getInstance(),
        LocalRepository.getInstance(context, preferences)
    )
}