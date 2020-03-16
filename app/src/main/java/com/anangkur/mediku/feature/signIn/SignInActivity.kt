package com.anangkur.mediku.feature.signIn

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity: BaseActivity<SignInViewModel>(), SignInActionListener {

    override val mLayout: Int
        get() = R.layout.activity_sign_in
    override val mViewModel: SignInViewModel
        get() = obtainViewModel(SignInViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, SignInActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupTextWatcher()
        observeViewModel()
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressSignUpLive.observe(this@SignInActivity, Observer {
                if (it){
                    btn_signin.showProgress()
                }else{
                    btn_signin.hideProgress()
                }
            })
            resultSignUpLive.observe(this@SignInActivity, Observer {
                showToastShort("SignIn Success: ${it.email}")
            })
            errorSignUpLive.observe(this@SignInActivity, Observer {
                showSnackbarShort(it)
            })
        }
    }

    private fun setupView(){
        btn_signin.setOnClickListener { this.onClickSignIn(et_email.text.toString(), et_password.text.toString()) }
        btn_forgot_password.setOnClickListener { this.onClickForgot() }
        btn_register.setOnClickListener { this.onClickSignUp() }
        btn_signin_google.setOnClickListener { this.onClickGoogle() }
    }

    override fun onClickSignIn(email: String?, password: String?) {
        validateInput(email, password)
    }

    override fun onClickForgot() {

    }

    override fun onClickSignUp() {

    }

    override fun onClickGoogle() {

    }

    private fun validateInput(email: String?, password: String?){
        when {
            email.isNullOrEmpty() -> {
                til_email.isErrorEnabled = true
                til_email.error = getString(R.string.error_email_empty)
            }
            !email.validateEmail() -> {
                til_email.isErrorEnabled = true
                til_email.error = getString(R.string.error_email_not_valid)
            }
            password.isNullOrEmpty() -> {
                til_password.isErrorEnabled = true
                til_password.error = getString(R.string.error_password_empty)
            }
            !password.validatePassword() -> {
                til_password.isErrorEnabled = true
                til_password.error = getString(R.string.error_password_not_valid)
            }
            else -> {
                til_email.isErrorEnabled = false
                til_password.isErrorEnabled = false
                mViewModel.firebaseSignIn(email, password)
            }
        }
    }

    private fun setupTextWatcher(){
        et_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validateEmail() -> {
                        til_email.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_password.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validatePassword() -> {
                        til_password.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
}
