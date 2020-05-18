package com.anangkur.mediku.feature.medicalRecords.listMedicalRecords

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.feature.covid.covid19.CovidActivity
import com.anangkur.mediku.feature.medicalRecords.addMedicalRecord.AddMedicalRecordActivity
import com.anangkur.mediku.feature.medicalRecords.detailMedicalRecord.DetailMedicalRecordActivity
import com.anangkur.mediku.util.obtainViewModel
import com.anangkur.mediku.util.setupRecyclerViewLinear
import com.anangkur.mediku.util.showSnackbarLong
import kotlinx.android.synthetic.main.activity_medical_records.*
import kotlinx.android.synthetic.main.layout_toolbar_back.*


class MedicalRecordsActivity: BaseActivity<MedicalRecordsViewModel>(), MedicalRecordsActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MedicalRecordsActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_medical_records
    override val mViewModel: MedicalRecordsViewModel
        get() = obtainViewModel(MedicalRecordsViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.label_medical_record)

    private lateinit var mAdapter: MedicalRecordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()
        observeViewModel()
        swipe_home.setOnRefreshListener {
            mAdapter.resetRecyclerData()
            mViewModel.getMedicalRecord()
        }
        btn_add_medical_report.setOnClickListener { this.onClickAddMedicalRecord() }
    }

    override fun onResume() {
        super.onResume()
        mAdapter.resetRecyclerData()
        mViewModel.getMedicalRecord()
    }

    private fun setupAdapter(){
        mAdapter = MedicalRecordsAdapter(this)
        recycler_home.apply {
            adapter = mAdapter
            setupRecyclerViewLinear(this@MedicalRecordsActivity, RecyclerView.VERTICAL)
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetMedicalRecord.observe(this@MedicalRecordsActivity, Observer {
                swipe_home.isRefreshing = it
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
