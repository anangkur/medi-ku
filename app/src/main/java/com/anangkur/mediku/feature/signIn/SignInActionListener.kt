package com.anangkur.mediku.feature.signIn

interface SignInActionListener {
    fun onClickSignIn(email: String?, password: String?)
    fun onClickForgot()
    fun onClickSignUp()
    fun onClickGoogle()
}