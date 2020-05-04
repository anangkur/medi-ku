package com.anangkur.mediku.feature.profile.editProfile

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
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.util.*
import com.esafirm.imagepicker.features.ImagePicker
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*
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
        btn_save.setOnClickListener { this.onClickSave(
            et_name.text.toString(),
            et_height.text.toString(),
            et_weight.text.toString()
        ) }
        btn_edit_photo.setOnClickListener { this.onCLickImage() }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getUserProfile()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            cropImage(data, true)
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
                    layout_profile.gone()
                }else{
                }
            })
            successGetProfile.observe(this@EditProfileActivity, Observer {
                layout_profile.visible()
                setupView(it)
            })
            errorGetProfile.observe(this@EditProfileActivity, Observer {
                showSnackbarLong(it)
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
                showSnackbarShort(getString(R.string.message_success_edit_profile))
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
                editProfile(user!!.apply { photo = it.toString() })
                iv_profile.setImageUrl(it.toString())
            })
        }
    }

    private fun setupView(data: User){
        mViewModel.user = data
        et_name.setText(data.name)
        et_height.setText(data.height.toString())
        et_weight.setText(data.weight.toString())
        iv_profile.setImageUrl(data.photo)
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
        et_height.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {
                when {
                    s.length > 1 && s.startsWith("0") -> {
                        s.delete(0,1)
                    }
                    s.isNullOrEmpty() -> {
                        et_height.setText("0")
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        et_weight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {
                when {
                    s.length > 1 && s.startsWith("0") -> {
                        s.delete(0,1)
                    }
                    s.isNullOrEmpty() -> {
                        et_weight.setText("0")
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    override fun onClickSave(name: String, height: String, weight: String) {
        when {
            !name.validateName() -> {
                til_name.isErrorEnabled = true
                til_name.error = getString(R.string.error_name_empty)
            }
            else -> {
                mViewModel.editProfile(mViewModel.user!!.apply {
                    this.name = name
                    this.height = height.toInt()
                    this.weight = weight.toInt()
                })
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
