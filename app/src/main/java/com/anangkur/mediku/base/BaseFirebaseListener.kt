package com.anangkur.mediku.base

interface BaseFirebaseListener<T> {
    fun onLoading(isLoading: Boolean)
    fun onSuccess(data: T)
    fun onFailed(errorMessage: String)
}