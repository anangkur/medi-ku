package com.anangkur.mediku.feature.addMedicalRecord

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.base.BaseSpinnerListener
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.feature.detailMedicalRecord.DetailMedicalRecordActivity.Companion.EXTRA_DETAIL_MEDICAL_RECORD
import com.anangkur.mediku.util.*
import com.esafirm.imagepicker.features.ImagePicker
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add_medical_record.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.io.File

class AddMedicalRecordActivity: BaseActivity<AddMedicalRecordViewModel>(), AddMedicalActionListener {

    companion object{
        const val REQ_EDIT = 100
        const val RES_EDIT = 200
        fun startActivity(context: Context){
            context.startActivity(Intent(context, AddMedicalRecordActivity::class.java))
        }
        fun startActivity(activity: AppCompatActivity, context: Context, data: MedicalRecord){
            activity.startActivityForResult(Intent(context, AddMedicalRecordActivity::class.java)
                .putExtra(EXTRA_DETAIL_MEDICAL_RECORD, data), REQ_EDIT)
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_add_medical_record
    override val mViewModel: AddMedicalRecordViewModel
        get() = obtainViewModel(AddMedicalRecordViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_add_medical_record)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        setupSpinner(mViewModel.createCategoryList())
        getDataFromIntent()
        setupDataToView(mViewModel.medicalRecord)
        btn_save.setOnClickListener {
            this.onClickSave(
                category = mViewModel.selectedCategory?:"",
                heartRate = et_heart_rate.text.toString(),
                bodyTemperature = et_temperature.text.toString(),
                bloodPressure = et_blood_pressure.text.toString(),
                diagnose = et_diagnose.text.toString()
            )
        }
        btn_select_category.setOnClickListener { this.onClickCategory() }
        btn_upload_document.setOnClickListener { this.onClickImage() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            cropImage(data, false)
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            handleImageCropperResult(data, resultCode, object: CompressImageListener{
                override fun progress(isLoading: Boolean) {
                    mViewModel.progressUploadDocument.postValue(isLoading)
                }
                override fun success(data: File) {
                    mViewModel.uploadDocument(Uri.fromFile(data))
                }
                override fun error(errorMessage: String) {
                    showSnackbarLong(errorMessage)
                }
            })
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressAddMedicalRecord.observe(this@AddMedicalRecordActivity, Observer {
                if (it){
                    btn_save.showProgress()
                }else{
                    btn_save.hideProgress()
                }
            })
            progressUploadDocument.observe(this@AddMedicalRecordActivity, Observer {
                if (it){
                    pb_document.visible()
                    iv_camera.gone()
                }else{
                    pb_document.gone()
                    iv_camera.visible()
                }
            })
            successUploadDocument.observe(this@AddMedicalRecordActivity, Observer {
                document = it.toString()
                iv_document.setImageUrl(it.toString())
                iv_camera.gone()
            })
            successAddMedicalRecord.observe(this@AddMedicalRecordActivity, Observer {
                showToastShort(getString(R.string.message_success_add_medical_report))
                if (medicalRecord != null){
                    setResult(RES_EDIT, Intent().putExtra(EXTRA_DETAIL_MEDICAL_RECORD, it))
                }
                finish()
            })
            errorAddMedicalRecord.observe(this@AddMedicalRecordActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    private fun getDataFromIntent(){
        if (intent.hasExtra(EXTRA_DETAIL_MEDICAL_RECORD)){
            mViewModel.medicalRecord = intent.getParcelableExtra(EXTRA_DETAIL_MEDICAL_RECORD)
        }
    }

    private fun setupDataToView(data: MedicalRecord?){
        if (data != null){
            setupImage(data.document)
            spinner_category.setSelection(mViewModel.createCategoryList().indexOf(data.category))
            et_diagnose.setText(data.diagnosis)
            et_blood_pressure.setText(data.bloodPressure.toString())
            et_temperature.setText(data.bodyTemperature.toString())
            et_heart_rate.setText(data.heartRate.toString())
        }
    }

    override fun onClickSave(
        category: String,
        diagnose: String?,
        bloodPressure: String?,
        bodyTemperature: String?,
        heartRate: String?
    ) {
        validateInput(category, diagnose, bloodPressure, bodyTemperature, heartRate)
    }

    override fun onClickCategory() {
        spinner_category.performClick()
    }

    override fun onClickImage() {
        showDialogImagePicker(object: DialogImagePickerActionListener{
            override fun onClickCamera() {
                ImagePicker.cameraOnly().start(this@AddMedicalRecordActivity)
            }
            override fun onClickGallery() {
                ImagePicker.create(this@AddMedicalRecordActivity)
                    .single()
                    .showCamera(false)
                    .start()
            }
        })
    }

    private fun validateInput(
        category: String,
        diagnose: String?,
        bloodPressure: String?,
        bodyTemperature: String?,
        heartRate: String?
    ){
        when {
            diagnose.isNullOrEmpty() -> {
                til_diagnose.setErrorMessage(getString(R.string.error_diagnose_empty))
            }
            bloodPressure.isNullOrEmpty() -> {
                til_blood_pressure.setErrorMessage(getString(R.string.error_blood_pressure_empty))
            }
            bodyTemperature.isNullOrEmpty() -> {
                til_temperature.setErrorMessage(getString(R.string.error_body_temperature_empty))
            }
            heartRate.isNullOrEmpty() -> {
                til_heart_rate.setErrorMessage(getString(R.string.error_heart_rate_empty))
            }
            else -> {
                mViewModel.addMedicalRecord(MedicalRecord(
                    category = category,
                    bloodPressure = bloodPressure.toInt(),
                    bodyTemperature = bodyTemperature.toInt(),
                    createdAt = if (mViewModel.medicalRecord == null) getCurrentTimeStamp()?:"1990-01-01 00:00:00"
                                else mViewModel.medicalRecord?.createdAt?:"",
                    diagnosis = diagnose,
                    heartRate = heartRate.toInt(),
                    updateAt = getCurrentTimeStamp()?:"1990-01-01 00:00:00",
                    document = mViewModel.document
                ))
            }
        }
    }

    private fun setupSpinner(data: List<String>){
        spinner_category.setupSpinner(data, object: BaseSpinnerListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setupCategoryView(parent?.selectedItem.toString())
            }
        })
    }

    private fun setupCategoryView(category: String){
        mViewModel.selectedCategory = category
        val resource = when (category){
            Const.CATEGORY_SICK -> {
                Pair(R.drawable.ic_pills, R.drawable.rect_rounded_4dp_gradient_blue)
            }
            Const.CATEGORY_HOSPITAL -> {
                Pair(R.drawable.ic_first_aid_kit, R.drawable.rect_rounded_4dp_gradient_purple)
            }
            Const.CATEGORY_CHECKUP -> {
                Pair(R.drawable.ic_healthy, R.drawable.rect_rounded_4dp_gradient_green)
            }
            else -> Pair(0,0)
        }
        iv_category.setImageResource(resource.first)
        btn_select_category.background = ContextCompat.getDrawable(this, resource.second)
        tv_category.text = category
    }

    private fun setupImage(imageUrl: String?){
        if (imageUrl != null){
            iv_document.setImageUrl(imageUrl)
            iv_camera.gone()
        }else{
            iv_camera.visible()
        }
    }

}
