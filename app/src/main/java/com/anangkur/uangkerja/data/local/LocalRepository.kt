package com.anangkur.uangkerja.data.local

import android.annotation.SuppressLint
import android.content.Context
import com.anangkur.uangkerja.data.DataSource

class LocalRepository(private val context: Context): DataSource {

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: LocalRepository? = null
        fun getInstance(context: Context) = INSTANCE ?: LocalRepository(context)
    }
}