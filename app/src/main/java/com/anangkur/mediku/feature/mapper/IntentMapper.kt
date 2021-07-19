package com.anangkur.mediku.feature.mapper

interface IntentMapper<INTENT, T> {
    fun mapToIntent(data: T): INTENT
    fun mapFromIntent(data: INTENT): T
}