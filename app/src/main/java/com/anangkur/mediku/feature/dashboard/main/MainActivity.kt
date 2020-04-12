package com.anangkur.mediku.feature.dashboard.main

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
import com.anangkur.mediku.feature.medicalRecords.addMedicalRecord.AddMedicalRecordActivity
import com.anangkur.mediku.feature.dashboard.main.home.HomeFragment
import com.anangkur.mediku.feature.dashboard.main.profile.ProfileFragment
import com.anangkur.mediku.util.ForceUpdateChecker
import com.anangkur.mediku.util.obtainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity<ViewModel>(), MainActionListener, ForceUpdateChecker.OnUpdateNeededListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_main
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

        fab_add.setOnClickListener { this.onClickAdd() }
        layout_btn_home.setOnClickListener { this.onClickHome() }
        layout_btn_profile.setOnClickListener { this.onClickProfile() }
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

    private fun setupHomeMenuEnable(){
        btn_home.setTextColor(ContextCompat.getColor(this, R.color.blue3))
        iv_btn_home.setImageResource(R.drawable.ic_dashboard_active)
        btn_profile.setTextColor(ContextCompat.getColor(this, R.color.grayDisable))
        iv_btn_profile.setImageResource(R.drawable.ic_profile_inactive)
    }

    private fun setupProfileMenuEnable(){
        btn_profile.setTextColor(ContextCompat.getColor(this, R.color.brown))
        iv_btn_profile.setImageResource(R.drawable.ic_profile_active)
        btn_home.setTextColor(ContextCompat.getColor(this, R.color.grayDisable))
        iv_btn_home.setImageResource(R.drawable.ic_dashboard_inactive)
    }
}
