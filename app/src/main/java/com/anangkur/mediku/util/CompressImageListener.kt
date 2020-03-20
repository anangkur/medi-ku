package com.anangkur.mediku.util

import java.io.File

interface CompressImageListener {
    fun progress(isLoading: Boolean)
    fun success(data: File)
    fun error(errorMessage: String)
}