package com.anangkur.mediku.feature.view.medicalRecords.addMedicalRecord

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
import com.anangkur.mediku.databinding.ActivityAddMedicalRecordBinding
import com.anangkur.mediku.feature.model.medical.MedicalRecordIntent
import com.anangkur.mediku.feature.view.medicalRecords.detailMedicalRecord.DetailMedicalRecordActivity.Companion.EXTRA_DETAIL_MEDICAL_RECORD
import com.anangkur.mediku.util.*
import com.annimon.stream.Stream
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.esafirm.imagepicker.features.ImagePicker
import com.theartofdev.edmodo.cropper.CropImage
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class   AddMedicalRecordActivity: BaseActivity<ActivityAddMedicalRecordBinding, AddMedicalRecordViewModel>(), AddMedicalActionListener {

    companion object{
        const val REQ_EDIT = 100
        const val RES_EDIT = 200
        fun startActivity(context: Context){
            context.startActivity(Intent(context, AddMedicalRecordActivity::class.java))
        }
        fun startActivity(activity: AppCompatActivity, context: Context, data: MedicalRecordIntent){
            activity.startActivityForResult(Intent(context, AddMedicalRecordActivity::class.java)
                .putExtra(EXTRA_DETAIL_MEDICAL_RECORD, data), REQ_EDIT)
        }
    }

    override val mViewModel: AddMedicalRecordViewModel
        get() = obtainViewModel(AddMedicalRecordViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_add_medical_record)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        setupSpinner(mViewModel.createCategoryList())
        getDataFromIntent()
        setupDataToView(mViewModel.medicalRecord)
        mLayout.btnSave.setOnClickListener {
            this.onClickSave(
                category = mViewModel.selectedCategory?:"",
                heartRate = mLayout.etHeartRate.text.toString(),
                bodyTemperature = mLayout.etTemperature.text.toString(),
                bloodPressure = mLayout.etBloodPressure.text.toString(),
                diagnose = mLayout.etDiagnose.text.toString(),
                date = mLayout.etDate.text.toString()
            )
        }
        mLayout.btnSelectCategory.setOnClickListener { this.onClickCategory() }
        mLayout.btnUploadDocument.setOnClickListener { this.onClickImage() }
        mLayout.etDate.setOnClickListener { this.onClickDate() }
    }

    override fun setupView(): ActivityAddMedicalRecordBinding {
        return ActivityAddMedicalRecordBinding.inflate(layoutInflater)
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
                setupProgressAddMedicalRecord(it)
            })
            progressUploadDocument.observe(this@AddMedicalRecordActivity, Observer {
                setupProgressUploadDocument(it)
            })
            successUploadDocument.observe(this@AddMedicalRecordActivity, Observer {
                setupDocument(it)
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

    private fun setupDataToView(data: MedicalRecordIntent?){
        if (data != null){
            val date = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT, Locale.US).parse(data.createdAt)
            val dateShow = SimpleDateFormat(Const.DATE_ENGLISH_YYYY_MM_DD, Locale.US).format(date!!)
            mLayout.etDate.setText(dateShow)
            setupImage(data.document)
            mLayout.spinnerCategory.setSelection(mViewModel.createCategoryList().indexOf(data.category))
            mLayout.etDiagnose.setText(data.diagnosis)
            mLayout.etBloodPressure.setText(data.bloodPressure.toString())
            mLayout.etTemperature.setText(data.bodyTemperature.toString())
            mLayout.etHeartRate.setText(data.heartRate.toString())
        }
    }

    private fun setupProgressAddMedicalRecord(isLoading: Boolean){
        if (isLoading){
            mLayout.btnSave.showProgress()
        }else{
            mLayout.btnSave.hideProgress()
        }
    }

    private fun setupProgressUploadDocument(isLoading: Boolean){
        if (isLoading){
            mLayout.pbDocument.visible()
            mLayout.ivCamera.gone()
        }else{
            mLayout.pbDocument.gone()
            mLayout.ivCamera.visible()
        }
    }

    private fun setupDocument(data: Uri){
        mViewModel.document = data.toString()
        mLayout.ivDocument.setImageUrl(data.toString())
        mLayout.ivCamera.gone()
    }

    override fun onClickSave(
        category: String,
        diagnose: String?,
        bloodPressure: String?,
        bodyTemperature: String?,
        heartRate: String?,
        date: String?
    ) {
        validateInput(category, diagnose, bloodPressure, bodyTemperature, heartRate, date)
    }

    override fun onClickCategory() {
        mLayout.spinnerCategory.performClick()
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

    override fun onClickDate() {
        val maximumDate = Calendar.getInstance()
        val builder = DatePickerBuilder(this, OnSelectDateListener {
            Stream.of(it).forEach { calendar ->
                val dateShow = SimpleDateFormat(Const.DATE_ENGLISH_YYYY_MM_DD, Locale.US).format(calendar.time)
                val datePost = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT, Locale.US).format(calendar.time)
                mLayout.etDate.setText(dateShow)
                mViewModel.selectedDate = datePost
            }
        })
            .pickerType(CalendarView.ONE_DAY_PICKER)
            .maximumDate(maximumDate)
            .headerColor(R.color.white)
            .headerLabelColor(R.color.colorPrimary)
            .selectionColor(R.color.colorPrimary)
            .todayLabelColor(R.color.colorPrimary)
            .dialogButtonsColor(R.color.colorPrimary)

        val datePicker = builder.build()
        datePicker.show()
    }

    private fun validateInput(
        category: String,
        diagnose: String?,
        bloodPressure: String?,
        bodyTemperature: String?,
        heartRate: String?,
        date: String?
    ){
        when {
            diagnose.isNullOrEmpty() -> {
                mLayout.tilDiagnose.setErrorMessage(getString(R.string.error_diagnose_empty))
            }
            bloodPressure.isNullOrEmpty() -> {
                mLayout.tilBloodPressure.setErrorMessage(getString(R.string.error_blood_pressure_empty))
            }
            bodyTemperature.isNullOrEmpty() -> {
                mLayout.tilTemperature.setErrorMessage(getString(R.string.error_body_temperature_empty))
            }
            heartRate.isNullOrEmpty() -> {
                mLayout.tilHeartRate.setErrorMessage(getString(R.string.error_heart_rate_empty))
            }
            date.isNullOrEmpty() -> {
                mLayout.tilDate.setErrorMessage(getString(R.string.error_date_empty))
            }
            else -> {
                mViewModel.addMedicalRecord(MedicalRecordIntent(
                    category = category,
                    bloodPressure = bloodPressure.toInt(),
                    bodyTemperature = bodyTemperature.toInt(),
                    createdAt = if (mViewModel.medicalRecord == null) mViewModel.selectedDate?:"1990-01-01 00:00:00"
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
        mLayout.spinnerCategory.setupSpinner(data, object: BaseSpinnerListener{
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
        mLayout.ivCategory.setImageResource(resource.first)
        mLayout.btnSelectCategory.background = ContextCompat.getDrawable(this, resource.second)
        mLayout.tvCategory.text = category
    }

    private fun setupImage(imageUrl: String?){
        if (imageUrl != null){
            mLayout.ivDocument.setImageUrl(imageUrl)
            mLayout.ivCamera.gone()
        }else{
            mLayout.ivCamera.visible()
        }
    }

}
