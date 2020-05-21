package com.anangkur.mediku.data.remote.mapper

interface RemoteMapper<REMOTE, T> {
    fun mapFromRemote(data: REMOTE): T
    fun mapToRemote(data: T): REMOTE
}