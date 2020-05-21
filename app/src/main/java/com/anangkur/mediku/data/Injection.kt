package com.anangkur.mediku.data

import android.content.Context
import com.anangkur.mediku.data.local.LocalRepository
import com.anangkur.mediku.data.remote.RemoteRepository

object Injection {
    fun provideRepository(context: Context) = Repository.getInstance(
        RemoteRepository.getInstance(),
        LocalRepository.getInstance(context)
    )
}