package com.anangkur.mediku.feature.auth.forgotPassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.util.obtainViewModel
import com.anangkur.mediku.util.showSnackbarLong
import com.anangkur.mediku.util.showToastShort
import com.anangkur.mediku.util.validateEmail
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity: BaseActivity<ForgotPasswordViewModel>(), ForgotPasswordActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, ForgotPasswordActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_forgot_password
    override val mViewModel: ForgotPasswordViewModel
        get() = obtainViewModel(ForgotPasswordViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTextWatcher()
        observeViewModel()
        btn_signin.setOnClickListener { this.onClickSendEmail(et_email.text.toString()) }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressForgotPassword.observe(this@ForgotPasswordActivity, Observer {
                if (it){
                    btn_signin.showProgress()
                }else{
                    btn_signin.hideProgress()
                }
            })
            successForgotPassword.observe(this@ForgotPasswordActivity, Observer {
                showToastShort(it)
                finish()
            })
            errorForgotPassword.observe(this@ForgotPasswordActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    private fun validateInput(email: String?){
        when {
            email.isNullOrEmpty() -> {
                til_email.isErrorEnabled = true
                til_email.error = getString(R.string.error_email_empty)
            }
            !email.validateEmail() -> {
                til_email.isErrorEnabled = true
                til_email.error = getString(R.string.error_email_not_valid)
            }
            else -> {
                mViewModel.sendResetEmail(email)
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
    }

    override fun onClickSendEmail(email: String?) {
        validateInput(email)
    }
}
