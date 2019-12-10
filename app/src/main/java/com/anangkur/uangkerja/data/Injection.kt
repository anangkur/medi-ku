package com.anangkur.uangkerja.data

import android.content.Context
import com.anangkur.uangkerja.data.local.LocalRepository
import com.anangkur.uangkerja.data.remote.RemoteRepository

object Injection {
    fun provideRepository(context: Context) = Repository.getInstance(
        RemoteRepository.getInstance(),
        LocalRepository.getInstance(context)
    )
}