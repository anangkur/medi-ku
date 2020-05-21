package com.anangkur.mediku.feature.medicalRecords.detailMedicalRecord

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.databinding.ActivityDetailMedicalRecordBinding
import com.anangkur.mediku.feature.medicalRecords.addMedicalRecord.AddMedicalRecordActivity
import com.anangkur.mediku.feature.medicalRecords.addMedicalRecord.AddMedicalRecordActivity.Companion.REQ_EDIT
import com.anangkur.mediku.feature.medicalRecords.addMedicalRecord.AddMedicalRecordActivity.Companion.RES_EDIT
import com.anangkur.mediku.util.*
import java.text.SimpleDateFormat
import java.util.*

class DetailMedicalRecordActivity: BaseActivity<ActivityDetailMedicalRecordBinding, DetailMedicalRecordViewModel>(), DetailMedicalRecordActionListener {

    companion object{
        const val EXTRA_DETAIL_MEDICAL_RECORD = "EXTRA_DETAIL_MEDICAL_RECORD"
        fun startActivity(context: Context, data: MedicalRecord){
            context.startActivity(Intent(context, DetailMedicalRecordActivity::class.java)
                .putExtra(EXTRA_DETAIL_MEDICAL_RECORD, data))
        }
    }

    override val mViewModel: DetailMedicalRecordViewModel
        get() = obtainViewModel(DetailMedicalRecordViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_detail_medical_record)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataFromIntent()
        setupDataToView(mViewModel.detailMedicalRecord)
        mLayout.btnEdit.setOnClickListener { this.onClickEdit(mViewModel.detailMedicalRecord) }
    }

    override fun setupView(): ActivityDetailMedicalRecordBinding {
        return ActivityDetailMedicalRecordBinding.inflate(layoutInflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            resultCode == RES_EDIT && requestCode == REQ_EDIT -> {
                val medicalRecord = data?.getParcelableExtra<MedicalRecord>(EXTRA_DETAIL_MEDICAL_RECORD)
                if (medicalRecord != null){
                    setupDataToView(medicalRecord)
                }
            }
        }
    }

    private fun getDataFromIntent(){
        if (intent.hasExtra(EXTRA_DETAIL_MEDICAL_RECORD)){
            mViewModel.detailMedicalRecord = intent.getParcelableExtra(EXTRA_DETAIL_MEDICAL_RECORD)
        }
    }

    private fun setupDataToView(data: MedicalRecord){
        val date = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT, Locale.US).parse(data.createdAt)
        val dateShow = SimpleDateFormat(Const.DATE_ENGLISH_YYYY_MM_DD, Locale.US).format(date)
        mLayout.tvDate.text = dateShow
        setupCategoryView(data.category)
        setupImage(data.document)
        mLayout.tvDiagnose.text = data.diagnosis
        mLayout.tvBloodPressure.text = "${data.bloodPressure} mmHg"
        mLayout.tvBodyTemperature.text = "${data.bodyTemperature} Celcius"
        mLayout.tvHeartRate.text = "${data.heartRate} Bpm"
    }

    private fun setupCategoryView(category: String){
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
            mLayout.btnUploadDocument.visible()
            mLayout.ivDocument.setImageUrl(imageUrl)
            mLayout.btnUploadDocument.setOnClickListener { this.onCLickImage(imageUrl) }
        }else{
            mLayout.btnUploadDocument.gone()
        }
    }

    override fun onClickEdit(data: MedicalRecord) {
        AddMedicalRecordActivity.startActivity(this,this,  data)
    }

    override fun onCLickImage(imageUrl: String) {
        this.showPreviewImage(imageUrl)
    }
}
