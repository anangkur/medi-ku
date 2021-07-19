package com.anangkur.mediku.feature.view.dashboard.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.databinding.ActivityMainBinding
import com.anangkur.mediku.feature.view.medicalRecords.addMedicalRecord.AddMedicalRecordActivity
import com.anangkur.mediku.feature.view.dashboard.main.home.HomeFragment
import com.anangkur.mediku.feature.view.dashboard.main.profile.ProfileFragment
import com.anangkur.mediku.util.ForceUpdateChecker
import com.anangkur.mediku.util.obtainViewModel

class MainActivity: BaseActivity<ActivityMainBinding, ViewModel>(), MainActionListener, ForceUpdateChecker.OnUpdateNeededListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }
        fun startActivityClearStack(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }
    }

    override val mViewModel: ViewModel
        get() = obtainViewModel(MainViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = null
    override val mTitleToolbar: String?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check()

        showHomeFragment()

        mLayout.fabAdd.setOnClickListener { this.onClickAdd() }
        mLayout.layoutBtnHome.setOnClickListener { this.onClickHome() }
        mLayout.layoutBtnProfile.setOnClickListener { this.onClickProfile() }
    }

    override fun setupView(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    private fun showHomeFragment(){
        setupHomeMenuEnable()
        val fragment = HomeFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .commit()
    }

    private fun showProfileFragment(){
        setupProfileMenuEnable()
        val fragment = ProfileFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_main, fragment)
            .commit()
    }

    override fun onClickHome() {
        showHomeFragment()
    }

    override fun onClickProfile() {
        showProfileFragment()
    }

    override fun onClickAdd() {
        AddMedicalRecordActivity.startActivity(this)
    }

    override fun onUpdateNeeded(updateUrl: String?) {
        val dialog: AlertDialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.message_new_version_available))
            .setMessage(getString(R.string.message_please_update))
            .setPositiveButton(getString(R.string.btn_update)) { dialog, _ ->
                dialog.dismiss()
                redirectStore(updateUrl)
            }
            .setNegativeButton(getString(R.string.btn_no_thanks)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun redirectStore(updateUrl: String?) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun setupHomeMenuEnable(){
        mLayout.btnHome.setTextColor(ContextCompat.getColor(this, R.color.blue3))
        mLayout.ivBtnHome.setImageResource(R.drawable.ic_dashboard_active)
        mLayout.btnProfile.setTextColor(ContextCompat.getColor(this, R.color.grayDisable))
        mLayout.ivBtnProfile.setImageResource(R.drawable.ic_profile_inactive)
    }

    private fun setupProfileMenuEnable(){
        mLayout.btnProfile.setTextColor(ContextCompat.getColor(this, R.color.brown))
        mLayout.ivBtnProfile.setImageResource(R.drawable.ic_profile_active)
        mLayout.btnHome.setTextColor(ContextCompat.getColor(this, R.color.grayDisable))
        mLayout.ivBtnHome.setImageResource(R.drawable.ic_dashboard_inactive)
    }
}
