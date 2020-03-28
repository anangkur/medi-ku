package com.anangkur.mediku.data.model

data class BaseResult<out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): BaseResult<T> {
            return BaseResult(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): BaseResult<T> {
            return BaseResult(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): BaseResult<T> {
            return BaseResult(Status.LOADING, data, null)
        }
    }
}