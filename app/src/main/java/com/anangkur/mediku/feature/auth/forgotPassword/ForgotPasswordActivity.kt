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
import com.anangkur.mediku.databinding.ActivityForgotPasswordBinding
import com.anangkur.mediku.util.obtainViewModel
import com.anangkur.mediku.util.showSnackbarLong
import com.anangkur.mediku.util.showToastShort
import com.anangkur.mediku.util.validateEmail

class ForgotPasswordActivity: BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel>(), ForgotPasswordActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, ForgotPasswordActivity::class.java))
        }
    }

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
        mLayout.btnSignin.setOnClickListener { this.onClickSendEmail(mLayout.etEmail.text.toString()) }
    }

    override fun setupView(): ActivityForgotPasswordBinding {
        return ActivityForgotPasswordBinding.inflate(layoutInflater)
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressForgotPassword.observe(this@ForgotPasswordActivity, Observer {
                setupLoading(it)
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
                mLayout.tilEmail.isErrorEnabled = true
                mLayout.tilEmail.error = getString(R.string.error_email_empty)
            }
            !email.validateEmail() -> {
                mLayout.tilEmail.isErrorEnabled = true
                mLayout.tilEmail.error = getString(R.string.error_email_not_valid)
            }
            else -> {
                mViewModel.sendResetEmail(email)
            }
        }
    }

    private fun setupTextWatcher(){
        mLayout.etEmail.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validateEmail() -> {
                        mLayout.tilEmail.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setupLoading(isLoading: Boolean){
        if (isLoading){
            mLayout.btnSignin.showProgress()
        }else{
            mLayout.btnSignin.hideProgress()
        }
    }

    override fun onClickSendEmail(email: String?) {
        validateInput(email)
    }
}
