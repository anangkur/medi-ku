package com.anangkur.uangkerja.data

import com.anangkur.uangkerja.data.local.LocalRepository
import com.anangkur.uangkerja.data.remote.RemoteRepository

class Repository(private val remoteRepository: RemoteRepository, private val localRepository: LocalRepository) {

    companion object{
        @Volatile private var INSTANCE: Repository? = null
        fun getInstance(remoteRepository: RemoteRepository, localRepository: LocalRepository) = INSTANCE ?: synchronized(
            Repository::class.java){
            INSTANCE ?: Repository(remoteRepository, localRepository).also { INSTANCE = it }
        }
    }
}