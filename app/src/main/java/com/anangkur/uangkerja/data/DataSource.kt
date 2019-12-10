package com.anangkur.uangkerja.data

import com.anangkur.uangkerja.data.model.BaseResponse
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.data.model.auth.Register
import com.anangkur.uangkerja.data.model.auth.ResponseLogin
import com.anangkur.uangkerja.data.model.profile.ResponseUser

interface DataSource {
    suspend fun postLogin(email: String, password: String): Result<ResponseLogin>{throw Exception()}
    suspend fun postSignup(name: String, email: String, password: String, passwordConfirm: String): Result<BaseResponse<Register>>{throw Exception()}
    suspend fun getProfile(): Result<ResponseUser>{throw Exception()}

    fun saveApiToken(apiToken: String){ throw Exception() }
    fun loadApiToken(): String? { throw Exception() }
    fun deleteApiToken(){ throw Exception() }


}