package com.anangkur.mediku.feature.auth.signIn

interface SignInActionListener {
    fun onClickSignIn(email: String?, password: String?)
    fun onClickForgot()
    fun onClickSignUp()
    fun onClickGoogle()
}