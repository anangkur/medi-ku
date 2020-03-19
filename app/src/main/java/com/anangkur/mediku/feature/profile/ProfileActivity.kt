package com.anangkur.mediku.feature.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.base.BaseErrorView
import com.anangkur.mediku.feature.editPassword.EditPasswordActivity
import com.anangkur.mediku.feature.editProfile.EditProfileActivity
import com.anangkur.mediku.feature.signIn.SignInActivity
import com.anangkur.mediku.util.*
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ProfileActivity: BaseActivity<ProfileViewModel>(), ProfileActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_profile
    override val mViewModel: ProfileViewModel
        get() = obtainViewModel(ProfileViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        btn_logout.setOnClickListener { this.onClickLogout() }
        btn_edit_profile.setOnClickListener { this.onClickEditProfile() }
        btn_edit_password.setOnClickListener { this.onClickEditPassword() }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getUserProfile()
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetProfile.observe(this@ProfileActivity, Observer {
                if (it){
                    ev_profile.visible()
                    ev_profile.showProgress()
                    layout_profile.gone()
                }else{
                    ev_profile.endProgress()
                }
            })
            successGetProfile.observe(this@ProfileActivity, Observer {
                ev_profile.gone()
                if (it.first){
                    layout_profile.visible()
                    setupView(it.second!!)
                }else{
                    SignInActivity.startActivityClearStack(this@ProfileActivity)
                }
            })
            errorGetProfile.observe(this@ProfileActivity, Observer {
                ev_profile.showError(it, getString(R.string.btn_retry), BaseErrorView.ERROR_GENERAL)
                ev_profile.setRetryClickListener { getUserProfile() }
            })
            progressLogout.observe(this@ProfileActivity, Observer {
                if (it){
                    tv_logout.gone()
                    pb_logout.visible()
                }else{
                    tv_logout.visible()
                    pb_logout.gone()
                }
            })
            successLogout.observe(this@ProfileActivity, Observer {
                SignInActivity.startActivityClearStack(this@ProfileActivity)
            })
            errorLogout.observe(this@ProfileActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    private fun setupView(data: FirebaseUser){
        tv_name.text = data.displayName
        tv_email.text = data.email
        iv_profile.setImageUrl(data.photoUrl?.toString()?:"")
        setupEditPassword(data)
    }

    private fun setupEditPassword(user: FirebaseUser){
        val provider = user.providerData
        for (userInfo in provider){
            when (userInfo.providerId){
                Const.PROVIDER_FIREBASE -> { }
                Const.PROVIDER_GOOGLE -> { btn_edit_password.gone() }
                Const.PROVIDER_PASSWORD -> { btn_edit_password.visible() }
            }
        }
    }

    override fun onClickEditProfile() {
        EditProfileActivity.startActivity(this)
    }

    override fun onClickEditPassword() {
        EditPasswordActivity.startActivity(this)
    }

    override fun onClickLogout() {
        mViewModel.logout()
    }
}
