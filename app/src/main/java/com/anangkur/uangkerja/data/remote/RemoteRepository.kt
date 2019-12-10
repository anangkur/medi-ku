package com.anangkur.uangkerja.data.remote

import com.anangkur.uangkerja.base.BaseDataSource
import com.anangkur.uangkerja.data.DataSource
import com.anangkur.uangkerja.data.model.BaseResponse
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.data.model.auth.Register
import com.anangkur.uangkerja.data.model.auth.ResponseLogin
import com.anangkur.uangkerja.data.model.profile.ResponseUser

class RemoteRepository: DataSource, BaseDataSource() {

    override suspend fun postLogin(email: String, password: String): Result<ResponseLogin> {
        return getResult { ApiService.getApiService.postLogin(email, password) }
    }

    override suspend fun postSignup(
        name: String,
        email: String,
        password: String,
        passwordConfirm: String
    ): Result<BaseResponse<Register>> {
        return getResult { ApiService.getApiService.postRegister(name, email, password, passwordConfirm) }
    }

    override suspend fun getProfile(): Result<ResponseUser> {
        return getResult { ApiService.getApiService.getUserProfile() }
    }

    companion object{
        private var INSTANCE: RemoteRepository? = null
        fun getInstance() = INSTANCE ?: RemoteRepository()
    }
}