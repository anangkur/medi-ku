package com.anangkur.mediku.feature.medicalRecords.listMedicalRecords

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.feature.medicalRecords.addMedicalRecord.AddMedicalRecordActivity
import com.anangkur.mediku.feature.covid.covid19.CovidActivity
import com.anangkur.mediku.feature.medicalRecords.detailMedicalRecord.DetailMedicalRecordActivity
import com.anangkur.mediku.feature.profile.userProfile.ProfileActivity
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.activity_medical_records.*


class MedicalRecordsActivity: BaseActivity<MedicalRecordsViewModel>(), MedicalRecordsActionListener, ForceUpdateChecker.OnUpdateNeededListener {

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
        get() = toolbar_home
    override val mTitleToolbar: String?
        get() = null

    private lateinit var mAdapter: MedicalRecordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check()
        setupAdapter()
        observeViewModel()
        swipe_home.setOnRefreshListener {
            mAdapter.resetRecyclerData()
            mViewModel.getMedicalRecord()
        }
        btn_profile.setOnClickListener { this.onClickProfile() }
        btn_add_medical_report.setOnClickListener { this.onClickAddMedicalRecord() }
        card_covid.setOnClickListener { this.onClickCovid() }
    }

    override fun onResume() {
        super.onResume()
        mAdapter.resetRecyclerData()
        mViewModel.getUserProfile()
        mViewModel.getMedicalRecord()
    }

    private fun setupToolbar(user: User){
        iv_toolbar_home.setImageUrl(user.photo)
        tv_toolbar_home.text = user.name
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
            progressGetProfile.observe(this@MedicalRecordsActivity, Observer {
                if (it){
                    iv_toolbar_home.invisible()
                    tv_toolbar_home.invisible()
                    pb_toolbar_home.visible()
                }else{
                    iv_toolbar_home.visible()
                    tv_toolbar_home.visible()
                    pb_toolbar_home.gone()
                }
            })
            successGetProfile.observe(this@MedicalRecordsActivity, Observer {
                setupToolbar(it)
            })
            errorGetProfile.observe(this@MedicalRecordsActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    override fun onClickProfile() {
        ProfileActivity.startActivity(this)
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

    override fun onUpdateNeeded(updateUrl: String?) {
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle("New version available")
            .setMessage("Please, update app to new version to continue reposting.")
            .setPositiveButton("Update") { dialog, which -> redirectStore(updateUrl) }
            .setNegativeButton("No, thanks") { dialog, which -> finish() }
            .create()
        dialog.show()
    }

    private fun redirectStore(updateUrl: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
