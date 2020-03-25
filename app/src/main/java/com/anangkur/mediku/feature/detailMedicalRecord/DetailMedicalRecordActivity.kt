package com.anangkur.mediku.feature.detailMedicalRecord

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.feature.addMedicalRecord.AddMedicalRecordActivity
import com.anangkur.mediku.feature.addMedicalRecord.AddMedicalRecordActivity.Companion.REQ_EDIT
import com.anangkur.mediku.feature.addMedicalRecord.AddMedicalRecordActivity.Companion.RES_EDIT
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.activity_detail_medical_record.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class DetailMedicalRecordActivity: BaseActivity<DetailMedicalRecordViewModel>(), DetailMedicalRecordActionListener {

    companion object{
        const val EXTRA_DETAIL_MEDICAL_RECORD = "EXTRA_DETAIL_MEDICAL_RECORD"
        fun startActivity(context: Context, data: MedicalRecord){
            context.startActivity(Intent(context, DetailMedicalRecordActivity::class.java)
                .putExtra(EXTRA_DETAIL_MEDICAL_RECORD, data))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_detail_medical_record
    override val mViewModel: DetailMedicalRecordViewModel
        get() = obtainViewModel(DetailMedicalRecordViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_detail_medical_record)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataFromIntent()
        setupDataToView(mViewModel.detailMedicalRecord)
        btn_edit.setOnClickListener { this.onClickEdit(mViewModel.detailMedicalRecord) }
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
        setupCategoryView(data.category)
        setupImage(data.document)
        tv_diagnose.text = data.diagnosis
        tv_blood_pressure.text = "${data.bloodPressure} mmHg"
        tv_body_temperature.text = "${data.bodyTemperature} Celcius"
        tv_heart_rate.text = "${data.heartRate} Bpm"
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
        iv_category.setImageResource(resource.first)
        btn_select_category.background = ContextCompat.getDrawable(this, resource.second)
        tv_category.text = category
    }

    private fun setupImage(imageUrl: String?){
        if (imageUrl != null){
            btn_upload_document.visible()
            iv_document.setImageUrl(imageUrl)
            btn_upload_document.setOnClickListener { this.onCLickImage(imageUrl) }
        }else{
            btn_upload_document.gone()
        }
    }

    override fun onClickEdit(data: MedicalRecord) {
        AddMedicalRecordActivity.startActivity(this,this,  data)
    }

    override fun onCLickImage(imageUrl: String) {
        this.showPreviewImage(imageUrl)
    }
}
