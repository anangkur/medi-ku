package com.anangkur.mediku.feature.profile.userProfile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.feature.about.AboutActivity
import com.anangkur.mediku.feature.auth.editPassword.EditPasswordActivity
import com.anangkur.mediku.feature.profile.editProfile.EditProfileActivity
import com.anangkur.mediku.feature.auth.signIn.SignInActivity
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*

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
                setupProgressGetProfile(it)
            })
            successGetProfile.observe(this@ProfileActivity, Observer {
                setupView(it)
            })
            errorGetProfile.observe(this@ProfileActivity, Observer {
                showSnackbarLong(it)
            })
            progressLogout.observe(this@ProfileActivity, Observer {
                setupProgressLogout(it)
            })
            successLogout.observe(this@ProfileActivity, Observer {
                SignInActivity.startActivityClearStack(this@ProfileActivity)
            })
            errorLogout.observe(this@ProfileActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    private fun setupView(data: User){
        layout_profile.visible()
        tv_name.text = data.name
        tv_email.text = data.email
        tv_height_weight.text = "Height: ${data.height}cm | Weight: ${data.weight}kg"
        iv_profile.setImageUrl(data.photo)
        iv_profile.setOnClickListener { this.onClickImage(data.photo) }
        setupEditPassword(data.providerName)
    }

    private fun setupProgressGetProfile(isLoading: Boolean){
        if (isLoading){
            pb_profile.visible()
            layout_profile.invisible()
        }else{
            pb_profile.gone()
            layout_profile.visible()
        }
    }

    private fun setupProgressLogout(isLoading: Boolean){
        if (isLoading){
            tv_logout.gone()
            pb_logout.visible()
        }else{
            tv_logout.visible()
            pb_logout.gone()
        }
    }

    private fun setupEditPassword(providerId: String){
        when (providerId){
            Const.PROVIDER_FIREBASE -> { }
            Const.PROVIDER_GOOGLE -> { btn_edit_password.gone() }
            Const.PROVIDER_PASSWORD -> { btn_edit_password.visible() }
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

    override fun onCLickAbout() {
        AboutActivity.startActivity(this)
    }

    override fun onClickImage(imageUrl: String) {
        this.showPreviewImage(imageUrl)
    }
}
