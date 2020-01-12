package com.anangkur.mediku.feature.signIn

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anangkur.mediku.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity(), LoginActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupView()
    }

    private fun setupView(){
        btn_signin.setOnClickListener { this.onClickSignIn(et_email.text.toString(), et_password.text.toString()) }
        btn_forgot_password.setOnClickListener { this.onClickForgot() }
        btn_register.setOnClickListener { this.onClickSignUp() }
        btn_signin_google.setOnClickListener { this.onClickGoogle() }
    }

    override fun onClickSignIn(email: String, password: String) {

    }

    override fun onClickForgot() {

    }

    override fun onClickSignUp() {

    }

    override fun onClickGoogle() {

    }
}
