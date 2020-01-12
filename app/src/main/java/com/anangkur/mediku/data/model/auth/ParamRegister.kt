package com.anangkur.mediku.data.model.auth

data class ParamRegister(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirm: String
)