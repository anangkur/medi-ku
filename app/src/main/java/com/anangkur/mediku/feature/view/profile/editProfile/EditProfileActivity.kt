package com.anangkur.mediku.feature.view.profile.editProfile

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
import com.anangkur.mediku.databinding.ActivityEditProfileBinding
import com.anangkur.mediku.util.*
import com.esafirm.imagepicker.features.ImagePicker
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File

class EditProfileActivity: BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>(), EditProfileActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, EditProfileActivity::class.java))
        }
    }

    override val mViewModel: EditProfileViewModel
        get() = obtainViewModel(EditProfileViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_edit_profile)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupTextWatcher()
        observeViewModel()
        mLayout.btnSave.setOnClickListener { this.onClickSave(
            mLayout.etName.text.toString(),
            mLayout.etHeight.text.toString(),
            mLayout.etWeight.text.toString()
        ) }
        mLayout.btnEditPhoto.setOnClickListener { this.onCLickImage() }
    }

    override fun setupView(): ActivityEditProfileBinding {
        return ActivityEditProfileBinding.inflate(layoutInflater)
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
                        mLayout.pbImageProfile.visible()
                    }else{
                        mLayout.pbImageProfile.gone()
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
                setupLoadingProfile(it)
            })
            successGetProfile.observe(this@EditProfileActivity, Observer {
                setupView(it)
            })

            // edit profile
            progressEditProfile.observe(this@EditProfileActivity, Observer {
                setupLoadingEditProfile(it)
            })
            successEditProfile.observe(this@EditProfileActivity, Observer {
                showSnackbarShort(getString(R.string.message_success_edit_profile))
            })
            errorEditProfile.observe(this@EditProfileActivity, Observer {
                showSnackbarLong(it)
            })

            // upload image
            progressUploadImage.observe(this@EditProfileActivity, Observer {
                setupLoadingUploadImage(it)
            })
            successUploadImage.observe(this@EditProfileActivity, Observer {
                editProfile(user!!.apply { photo = it.toString() })
                setupImage(it)
            })
        }
    }

    private fun setupView(data: User){
        mLayout.layoutProfile.visible()
        mViewModel.user = data
        mLayout.etName.setText(data.name)
        mLayout.etHeight.setText(data.height.toString())
        mLayout.etWeight.setText(data.weight.toString())
        mLayout.ivProfile.setImageUrl(data.photo)
    }

    private fun setupLoadingProfile(isLoading: Boolean){
        if (isLoading){
            mLayout.evEditProfile.visible()
            mLayout.evEditProfile.showProgress()
            mLayout.layoutProfile.gone()
        }else{
            mLayout.evEditProfile.gone()
            mLayout.layoutProfile.visible()
        }
    }

    private fun setupLoadingEditProfile(isLoading: Boolean){
        if (isLoading){
            mLayout.btnSave.showProgress()
        }else{
            mLayout.btnSave.hideProgress()
        }
    }

    private fun setupLoadingUploadImage(isLoading: Boolean){
        if (isLoading){
            mLayout.pbImageProfile.visible()
        }else{
            mLayout.pbImageProfile.gone()
        }
    }

    private fun setupImage(image: Uri){
        mLayout.ivProfile.setImageUrl(image.toString())
    }

    private fun setupTextWatcher(){
        mLayout.etName.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                when {
                    s.toString().validateName() -> {
                        mLayout.tilName.isErrorEnabled = false
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        mLayout.etHeight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {
                when {
                    s.length > 1 && s.startsWith("0") -> {
                        s.delete(0,1)
                    }
                    s.isEmpty() -> {
                        mLayout.etHeight.setText("0")
                    }
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        mLayout.etWeight.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {
                when {
                    s.length > 1 && s.startsWith("0") -> {
                        s.delete(0,1)
                    }
                    s.isEmpty() -> {
                        mLayout.etWeight.setText("0")
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
                mLayout.tilName.isErrorEnabled = true
                mLayout.tilName.error = getString(R.string.error_name_empty)
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
