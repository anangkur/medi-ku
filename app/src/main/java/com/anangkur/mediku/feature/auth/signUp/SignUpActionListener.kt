package com.anangkur.mediku.feature.auth.signUp

interface SignUpActionListener {
    fun onClickSignUp(name: String?, email: String?, password: String?, confirmPassword: String?)
    fun onClickSignIn()
    fun onClickSignUpGoogle()
}