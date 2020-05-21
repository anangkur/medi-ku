package com.anangkur.mediku.feature.view.medicalRecords.listMedicalRecords

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.databinding.ActivityMedicalRecordsBinding
import com.anangkur.mediku.feature.view.covid.covid19.CovidActivity
import com.anangkur.mediku.feature.view.medicalRecords.addMedicalRecord.AddMedicalRecordActivity
import com.anangkur.mediku.feature.view.medicalRecords.detailMedicalRecord.DetailMedicalRecordActivity
import com.anangkur.mediku.util.obtainViewModel
import com.anangkur.mediku.util.setupRecyclerViewLinear
import com.anangkur.mediku.util.showSnackbarLong

class MedicalRecordsActivity: BaseActivity<ActivityMedicalRecordsBinding, MedicalRecordsViewModel>(), MedicalRecordsActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MedicalRecordsActivity::class.java))
        }
    }

    override val mViewModel: MedicalRecordsViewModel
        get() = obtainViewModel(MedicalRecordsViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.label_medical_record)

    private lateinit var mAdapter: MedicalRecordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()
        observeViewModel()
        mLayout.swipeHome.setOnRefreshListener {
            mAdapter.resetRecyclerData()
            mViewModel.getMedicalRecord()
        }
        mLayout.btnAddMedicalReport.setOnClickListener { this.onClickAddMedicalRecord() }
    }

    override fun setupView(): ActivityMedicalRecordsBinding {
        return ActivityMedicalRecordsBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        mAdapter.resetRecyclerData()
        mViewModel.getMedicalRecord()
    }

    private fun setupAdapter(){
        mAdapter = MedicalRecordsAdapter(this)
        mLayout.recyclerHome.apply {
            adapter = mAdapter
            setupRecyclerViewLinear(this@MedicalRecordsActivity, RecyclerView.VERTICAL)
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetMedicalRecord.observe(this@MedicalRecordsActivity, Observer {
                mLayout.swipeHome.isRefreshing = it
            })
            successGetMedicalRecord.observe(this@MedicalRecordsActivity, Observer {
                mAdapter.setRecyclerData(it)
            })
            errorGetMedicalRecord.observe(this@MedicalRecordsActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    override fun onClickAddMedicalRecord() {
        AddMedicalRecordActivity.startActivity(this)
    }

    override fun onClickItem(data: MedicalRecord) {
        DetailMedicalRecordActivity.startActivity(this, data)
    }

    override fun onClickCovid() {
        CovidActivity.startActivity(this)
    }
}
