package com.anangkur.mediku.feature.auth.editPassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.feature.auth.signIn.SignInActivity
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.activity_edit_password.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

class EditPasswordActivity: BaseActivity<EditPasswordViewModel>(), EditPasswordActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, EditPasswordActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_edit_password
    override val mViewModel: EditPasswordViewModel
        get() = obtainViewModel(EditPasswordViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_edit_profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTextWatcher()
        observeViewModel()
        btn_save.setOnClickListener { this.onClickSave(
            oldPassword = et_password_old.text.toString(),
            confirmPassword = et_password_confirm.text.toString(),
            newPassword = et_password_new.text.toString()
        ) }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressEditPassword.observe(this@EditPasswordActivity, Observer {
                if (it){
                    btn_save.showProgress()
                }else{
                    btn_save.hideProgress()
                }
            })
            successEditPassword.observe(this@EditPasswordActivity, Observer {
                showToastShort(getString(R.string.message_success_edit_password))
                finish()
            })
            errorEditPassword.observe(this@EditPasswordActivity, Observer {
                showSnackbarLong(it)
            })
            errorAuth.observe(this@EditPasswordActivity, Observer {
                showToastShort(it)
                SignInActivity.startActivityClearStack(this@EditPasswordActivity)
            })
        }
    }

    private fun validateInput(oldPassword: String?, newPassword: String?, confirmPassword: String?){
        when {
            oldPassword.isNullOrEmpty() -> {
                til_password_old.isErrorEnabled = true
                til_password_old.error = getString(R.string.error_old_password_empty)
            }
            !oldPassword.validatePassword() -> {
                til_password_old.isErrorEnabled = true
                til_password_old.error = getString(R.string.error_password_not_valid)
            }
            newPassword.isNullOrEmpty() -> {
                til_password_new.isErrorEnabled = true
                til_password_new.error = getString(R.string.error_new_password_empty)
            }
            !newPassword.validatePassword() -> {
                til_password_new.isErrorEnabled = true
                til_password_new.error = getString(R.string.error_password_not_valid)
            }
            confirmPassword.isNullOrEmpty() -> {
                til_password_confirm.isErrorEnabled = true
                til_password_confirm.error = getString(R.string.error_confirm_password_empty)
            }
            !confirmPassword.validatePasswordConfirm(newPassword) -> {
                til_password_confirm.isErrorEnabled = true
                til_password_confirm.error = getString(R.string.error_password_confirm_not_valid)
            }
            else -> {
                mViewModel.editPassword(oldPassword, newPassword)
            }
        }
    }

    private fun setupTextWatcher(){
        et_password_old.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validatePassword() -> {
                        til_password_old.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_password_new.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validatePassword() -> {
                        til_password_new.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_password_confirm.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validatePasswordConfirm(et_password_new.text.toString()) -> {
                        til_password_confirm.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onClickSave(oldPassword: String?, newPassword: String?, confirmPassword: String?) {
        validateInput(oldPassword, newPassword, confirmPassword)
    }
}
