package com.anangkur.mediku.base

import retrofit2.Response
import com.anangkur.mediku.data.model.BaseResult

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): BaseResult<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return BaseResult.success(body)
            }
            return BaseResult.error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return BaseResult.error(e.message ?: e.toString())
        }
    }
}