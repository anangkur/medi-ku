package com.anangkur.uangkerja.feature.register

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
import com.anangkur.uangkerja.util.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class RegisterActivity: BaseActivity<RegisterViewModel>(), RegisterActionListener {

    override val mLayout: Int
        get() = R.layout.activity_register
    override val mViewModel: RegisterViewModel
        get() = obtainViewModel(RegisterViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_register)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        setupTextWatcher()
        btn_signup.setOnClickListener { this.onClickRegister() }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            registerLiveData.observe(this@RegisterActivity, Observer {
                when(it.status){
                    Result.Status.LOADING -> { showLoading() }
                    Result.Status.SUCCESS -> {
                        hideLoading()
                        this@RegisterActivity.showToastShort(it.message?:getString(R.string.message_success_register))
                        finish()
                    }
                    Result.Status.ERROR -> {
                        hideLoading()
                        this@RegisterActivity.showToastShort(it.message?:getString(R.string.error_default))
                    }
                }
            })
        }
    }

    private fun setupTextWatcher(){
        et_nama.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().validateName()){
                    til_nama.isErrorEnabled = false
                }else{
                    til_nama.isErrorEnabled = false
                    til_nama.error = getString(R.string.error_name)
                }
            }
        })
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
        et_password.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().validatePasswordConfirm(et_password.text.toString())){
                    til_password_confirm.isErrorEnabled = false
                }else{
                    til_password_confirm.isErrorEnabled = true
                    til_password_confirm.error = getString(R.string.error_password_confirm)
                }
            }
        })
    }

    private fun showLoading(){
        pb_btn_signup.visible()
        tv_btn_signup.gone()
        et_nama.disable()
        et_email.disable()
        et_password.disable()
        et_password_confirm.disable()
        mViewModel.isLoading = true
    }

    private fun hideLoading(){
        pb_btn_signup.gone()
        tv_btn_signup.visible()
        et_nama.enable()
        et_email.enable()
        et_password.enable()
        et_password_confirm.enable()
        mViewModel.isLoading = false
    }

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }

    override fun onClickRegister() {
        mViewModel.register(
            et_nama.text.toString(),
            et_email.text.toString(),
            et_password.text.toString(),
            et_password_confirm.text.toString())
    }
}
