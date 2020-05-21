package com.anangkur.mediku.feature.view.auth.editPassword

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.databinding.ActivityEditPasswordBinding
import com.anangkur.mediku.feature.view.auth.signIn.SignInActivity
import com.anangkur.mediku.util.*

class EditPasswordActivity: BaseActivity<ActivityEditPasswordBinding, EditPasswordViewModel>(), EditPasswordActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, EditPasswordActivity::class.java))
        }
    }

    override val mViewModel: EditPasswordViewModel
        get() = obtainViewModel(EditPasswordViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_edit_profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTextWatcher()
        observeViewModel()
        mLayout.btnSave.setOnClickListener { this.onClickSave(
            oldPassword = mLayout.etPasswordOld.text.toString(),
            confirmPassword = mLayout.etPasswordConfirm.text.toString(),
            newPassword = mLayout.etPasswordNew.text.toString()
        ) }
    }

    override fun setupView(): ActivityEditPasswordBinding {
        return ActivityEditPasswordBinding.inflate(layoutInflater)
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressEditPassword.observe(this@EditPasswordActivity, Observer {
                setupLoading(it)
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

    private fun setupLoading(isLoading: Boolean){
        if (isLoading){
            mLayout.btnSave.showProgress()
        }else{
            mLayout.btnSave.hideProgress()
        }
    }

    private fun validateInput(oldPassword: String?, newPassword: String?, confirmPassword: String?){
        when {
            oldPassword.isNullOrEmpty() -> {
                mLayout.tilPasswordOld.isErrorEnabled = true
                mLayout.tilPasswordOld.error = getString(R.string.error_old_password_empty)
            }
            !oldPassword.validatePassword() -> {
                mLayout.tilPasswordOld.isErrorEnabled = true
                mLayout.tilPasswordOld.error = getString(R.string.error_password_not_valid)
            }
            newPassword.isNullOrEmpty() -> {
                mLayout.tilPasswordNew.isErrorEnabled = true
                mLayout.tilPasswordNew.error = getString(R.string.error_new_password_empty)
            }
            !newPassword.validatePassword() -> {
                mLayout.tilPasswordNew.isErrorEnabled = true
                mLayout.tilPasswordNew.error = getString(R.string.error_password_not_valid)
            }
            confirmPassword.isNullOrEmpty() -> {
                mLayout.tilPasswordConfirm.isErrorEnabled = true
                mLayout.tilPasswordConfirm.error = getString(R.string.error_confirm_password_empty)
            }
            !confirmPassword.validatePasswordConfirm(newPassword) -> {
                mLayout.tilPasswordConfirm.isErrorEnabled = true
                mLayout.tilPasswordConfirm.error = getString(R.string.error_password_confirm_not_valid)
            }
            else -> {
                mViewModel.reAuthenticate(oldPassword, newPassword)
            }
        }
    }

    private fun setupTextWatcher(){
        mLayout.etPasswordOld.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validatePassword() -> {
                        mLayout.tilPasswordOld.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        mLayout.etPasswordNew.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validatePassword() -> {
                        mLayout.tilPasswordNew.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        mLayout.etPasswordConfirm.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    !s.isNullOrEmpty() || s.toString().validatePasswordConfirm(mLayout.etPasswordNew.text.toString()) -> {
                        mLayout.tilPasswordConfirm.isErrorEnabled = false
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
