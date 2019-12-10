package com.anangkur.uangkerja.data.remote

import com.anangkur.uangkerja.base.BaseDataSource
import com.anangkur.uangkerja.data.DataSource

class RemoteRepository: DataSource, BaseDataSource() {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance() = INSTANCE ?: RemoteRepository()
    }
}