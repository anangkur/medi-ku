package com.anangkur.mediku.data.remote

import com.anangkur.mediku.base.BaseDataSource
import com.anangkur.mediku.data.DataSource

class RemoteRepository: DataSource, BaseDataSource() {

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance() = INSTANCE ?: RemoteRepository()
    }
}