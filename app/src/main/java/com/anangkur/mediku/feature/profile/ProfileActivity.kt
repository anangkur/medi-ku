package com.anangkur.mediku.feature.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.base.BaseErrorView
import com.anangkur.mediku.feature.signIn.SignInActivity
import com.anangkur.mediku.util.gone
import com.anangkur.mediku.util.obtainViewModel
import com.anangkur.mediku.util.showSnackbarLong
import com.anangkur.mediku.util.visible
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
        mViewModel.getUserProfile()
        btn_logout.setOnClickListener { this.onClickLogout() }
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
                    SignInActivity.startActivity(this@ProfileActivity)
                    finish()
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
                SignInActivity.startActivity(this@ProfileActivity)
                finish()
            })
            errorLogout.observe(this@ProfileActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    private fun setupView(data: FirebaseUser){
        tv_name.text = data.displayName
        tv_email.text = data.email
    }

    override fun onClickEditProfile() {

    }

    override fun onClickEditPassword() {

    }

    override fun onClickLogout() {
        mViewModel.logout()
    }
}
