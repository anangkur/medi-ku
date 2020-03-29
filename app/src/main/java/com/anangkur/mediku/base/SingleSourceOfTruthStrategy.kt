package com.anangkur.mediku.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.anangkur.mediku.data.model.BaseResult
import kotlinx.coroutines.Dispatchers

fun <T, A> resultLiveData(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> BaseResult<A>,
    saveCallResult: suspend (A) -> Unit
): LiveData<BaseResult<T>> =
    liveData(Dispatchers.IO) {
        emit(BaseResult.loading())
        val responseStatus = networkCall.invoke()
        if (responseStatus.status == BaseResult.Status.SUCCESS) {
            saveCallResult(responseStatus.data!!)
            val source = databaseQuery.invoke().map { BaseResult.success(it) }
            emitSource(source)
        } else if (responseStatus.status == BaseResult.Status.ERROR) {
            emit(BaseResult.error(responseStatus.message!!))
            val source = databaseQuery.invoke().map { BaseResult.success(it) }
            emitSource(source)
        }
    }

fun <T> resultLiveData(
    databaseQuery: suspend () -> LiveData<T>
): LiveData<BaseResult<T>> =
    liveData(Dispatchers.IO) {
        emit(BaseResult.loading())
        val source = databaseQuery.invoke().map { BaseResult.success(it) }
        emitSource(source)
    }