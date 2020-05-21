package com.anangkur.mediku.feature.view.profile.userProfile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.databinding.ActivityProfileBinding
import com.anangkur.mediku.feature.view.about.AboutActivity
import com.anangkur.mediku.feature.view.auth.editPassword.EditPasswordActivity
import com.anangkur.mediku.feature.view.profile.editProfile.EditProfileActivity
import com.anangkur.mediku.feature.view.auth.signIn.SignInActivity
import com.anangkur.mediku.util.*

class ProfileActivity: BaseActivity<ActivityProfileBinding, ProfileViewModel>(), ProfileActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, ProfileActivity::class.java))
        }
    }

    override val mViewModel: ProfileViewModel
        get() = obtainViewModel(ProfileViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        mLayout.btnLogout.setOnClickListener { this.onClickLogout() }
        mLayout.btnEditProfile.setOnClickListener { this.onClickEditProfile() }
        mLayout.btnEditPassword.setOnClickListener { this.onClickEditPassword() }
    }

    override fun setupView(): ActivityProfileBinding {
        return ActivityProfileBinding.inflate(layoutInflater)
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
        mLayout.layoutProfile.visible()
        mLayout.tvName.text = data.name
        mLayout.tvEmail.text = data.email
        mLayout.tvHeightWeight.text = "Height: ${data.height}cm | Weight: ${data.weight}kg"
        mLayout.ivProfile.setImageUrl(data.photo)
        mLayout.ivProfile.setOnClickListener { this.onClickImage(data.photo) }
        setupEditPassword(data.providerName)
    }

    private fun setupProgressGetProfile(isLoading: Boolean){
        if (isLoading){
            mLayout.pbProfile.visible()
            mLayout.layoutProfile.invisible()
        }else{
            mLayout.pbProfile.gone()
            mLayout.layoutProfile.visible()
        }
    }

    private fun setupProgressLogout(isLoading: Boolean){
        if (isLoading){
            mLayout.tvLogout.gone()
            mLayout.pbLogout.visible()
        }else{
            mLayout.tvLogout.visible()
            mLayout.pbLogout.gone()
        }
    }

    private fun setupEditPassword(providerId: String){
        when (providerId){
            Const.PROVIDER_FIREBASE -> { }
            Const.PROVIDER_GOOGLE -> { mLayout.btnEditPassword.gone() }
            Const.PROVIDER_PASSWORD -> { mLayout.btnEditPassword.visible() }
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
