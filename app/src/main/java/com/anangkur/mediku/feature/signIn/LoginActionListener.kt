package com.anangkur.mediku.feature.signIn

interface LoginActionListener {
    fun onClickSignIn(email: String, password: String)
    fun onClickForgot()
    fun onClickSignUp()
    fun onClickGoogle()
}