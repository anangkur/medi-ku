package com.anangkur.uangkerja.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.uangkerja.R
import com.anangkur.uangkerja.base.BaseActivity
import com.anangkur.uangkerja.data.model.Result
import com.anangkur.uangkerja.feature.main.MainActivity
import com.anangkur.uangkerja.feature.register.RegisterActivity
import com.anangkur.uangkerja.util.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: BaseActivity<LoginViewModel>(), LoginActionListener {

    override val mLayout: Int
        get() = R.layout.activity_login
    override val mViewModel: LoginViewModel
        get() = obtainViewModel(LoginViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTextWatcher()
        observeViewModel()
        btn_login.setOnClickListener { this.onClickLogin() }
        btn_signup.setOnClickListener { this.onClickRegister() }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            loginLiveData.observe(this@LoginActivity, Observer {
                when(it.status){
                    Result.Status.LOADING -> { showLoading() }
                    Result.Status.SUCCESS -> {
                        hideLoading()
                        saveApiToken(it.data?.token?:"")
                        finish()
                        MainActivity.startActivity(this@LoginActivity)
                    }
                    Result.Status.ERROR -> {
                        hideLoading()
                        this@LoginActivity.showSnackbarShort(it.message?:getString(R.string.error_default))
                    }
                }
            })
        }
    }

    private fun setupTextWatcher(){
        et_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().validateEmail()){
                    til_email.isErrorEnabled = false
                }else{
                    til_email.isErrorEnabled = true
                    til_email.error = getString(R.string.error_email)
                }
            }
        })
        et_password.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().validatePassword()){
                    til_password.isErrorEnabled = false
                }else{
                    til_password.isErrorEnabled = true
                    til_password.error = getString(R.string.error_password)
                }
            }
        })
    }

    private fun showLoading(){
        tv_btn_login.gone()
        pb_btn_login.visible()
        et_email.disable()
        et_password.disable()
        mViewModel.isLoading = true
    }

    private fun hideLoading(){
        tv_btn_login.visible()
        pb_btn_login.gone()
        et_email.enable()
        et_password.enable()
        mViewModel.isLoading = false
    }

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onClickLogin() {
        mViewModel.login(et_email.text.toString(), et_password.text.toString())
    }

    override fun onClickRegister() {
        RegisterActivity.startActivity(this)
    }

    override fun onClickForgotPassword() {
        // todo implement forgot password
    }
}
