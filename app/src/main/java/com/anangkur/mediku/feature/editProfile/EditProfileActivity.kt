package com.anangkur.mediku.feature.editProfile

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.esafirm.imagepicker.features.ImagePicker
import com.google.firebase.auth.FirebaseUser
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.io.File

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
        btn_edit_photo.setOnClickListener { this.onCLickImage() }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getUserProfile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            cropImage(data)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            handleImageCropperResult(data, resultCode, object: CompressImageListener{
                override fun progress(isLoading: Boolean) {
                    if (isLoading){
                        pb_image_profile.visible()
                    }else{
                        pb_image_profile.gone()
                    }
                }
                override fun success(data: File) {
                    mViewModel.uploadImage(Uri.fromFile(data))
                }
                override fun error(errorMessage: String) {
                    showSnackbarLong(errorMessage)
                }
            })
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            // get profile
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
                    SignInActivity.startActivityClearStack(this@EditProfileActivity)
                }
            })
            errorGetProfile.observe(this@EditProfileActivity, Observer {
                ev_profile.showError(it, getString(R.string.btn_retry), BaseErrorView.ERROR_GENERAL)
                ev_profile.setRetryClickListener { getUserProfile() }
            })

            // edit profile
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
                showSnackbarLong(it)
            })

            // upload image
            progressUploadImage.observe(this@EditProfileActivity, Observer {
                if (it){
                    pb_image_profile.visible()
                }else{
                    pb_image_profile.gone()
                }
            })
            successUploadImage.observe(this@EditProfileActivity, Observer {
                iv_profile.setImageUrl(it.toString())
            })
            errorEditProfile.observe(this@EditProfileActivity, Observer {
                showSnackbarLong(it)
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

    override fun onCLickImage() {
        showDialogImagePicker(object: DialogImagePickerActionListener{
            override fun onClickCamera() {
                ImagePicker.cameraOnly().start(this@EditProfileActivity)
            }
            override fun onClickGallery() {
                ImagePicker.create(this@EditProfileActivity)
                    .single()
                    .showCamera(false)
                    .start()
            }
        })
    }
}
