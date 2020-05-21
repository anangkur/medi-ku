package com.anangkur.mediku.data.local.mapper

interface LocalMapper<LOCAL, T> {
    fun mapToLocal(data: T): LOCAL
    fun mapFromLocal(data: LOCAL): T
}