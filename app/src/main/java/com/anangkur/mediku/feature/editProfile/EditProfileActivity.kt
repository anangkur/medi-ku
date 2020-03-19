package com.anangkur.mediku.feature.editProfile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.base.BaseErrorView
import com.anangkur.mediku.feature.signIn.SignInActivity
import com.anangkur.mediku.util.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class EditProfileActivity: BaseActivity<EditProfileViewModel>(), EditProfileActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, EditProfileActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_edit_profile
    override val mViewModel: EditProfileViewModel
        get() = obtainViewModel(EditProfileViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_edit_profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTextWatcher()
        observeViewModel()
        btn_save.setOnClickListener { this.onClickSave(et_name.text.toString()) }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getUserProfile()
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetProfile.observe(this@EditProfileActivity, Observer {
                if (it){
                    ev_profile.visible()
                    ev_profile.showProgress()
                    layout_profile.gone()
                }else{
                    ev_profile.endProgress()
                }
            })
            successGetProfile.observe(this@EditProfileActivity, Observer {
                ev_profile.gone()
                if (it.first){
                    layout_profile.visible()
                    setupView(it.second!!)
                }else{
                    SignInActivity.startActivity(this@EditProfileActivity)
                    finish()
                }
            })
            errorGetProfile.observe(this@EditProfileActivity, Observer {
                ev_profile.showError(it, getString(R.string.btn_retry), BaseErrorView.ERROR_GENERAL)
                ev_profile.setRetryClickListener { getUserProfile() }
            })
            progressEditProfile.observe(this@EditProfileActivity, Observer {
                if (it){
                    btn_save.showProgress()
                }else{
                    btn_save.hideProgress()
                }
            })
            successEditProfile.observe(this@EditProfileActivity, Observer {
                showToastShort(getString(R.string.message_success_edit_profile))
                finish()
            })
            errorEditProfile.observe(this@EditProfileActivity, Observer {
                showSnackbarShort(it)
            })
        }
    }

    private fun setupView(data: FirebaseUser){
        et_name.setText(data.displayName)
        iv_profile.setImageUrl(data.photoUrl?.toString()?:"")
    }

    private fun setupTextWatcher(){
        et_name.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    s.toString().validateName() -> {
                        til_name.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onClickSave(name: String) {
        when {
            !name.validateName() -> {
                til_name.isErrorEnabled = true
                til_name.error = getString(R.string.error_name_empty)
            }
            else -> {
                mViewModel.editProfile(name)
            }
        }
    }
}
