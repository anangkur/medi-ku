package com.anangkur.mediku.feature.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.auth.User
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.feature.profile.ProfileActivity
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity: BaseActivity<HomeViewModel>(), HomeActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_home
    override val mViewModel: HomeViewModel
        get() = obtainViewModel(HomeViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar_home
    override val mTitleToolbar: String?
        get() = null

    private lateinit var mAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()
        observeViewModel()
        swipe_home.setOnRefreshListener {
            mAdapter.resetRecyclerData()
            mViewModel.getMedicalRecord()
        }
        btn_profile.setOnClickListener { this.onClickProfile() }
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
        mAdapter = HomeAdapter(this)
        recycler_home.apply {
            adapter = mAdapter
            setupRecyclerViewLinear(this@HomeActivity, RecyclerView.VERTICAL)
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetMedicalRecord.observe(this@HomeActivity, Observer {
                swipe_home.isRefreshing = it
            })
            successGetMedicalRecord.observe(this@HomeActivity, Observer {
                mAdapter.addRecyclerData(it)
            })
            errorGetMedicalRecord.observe(this@HomeActivity, Observer {
                showSnackbarLong(it)
            })
            progressGetProfile.observe(this@HomeActivity, Observer {
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
            successGetProfile.observe(this@HomeActivity, Observer {
                setupToolbar(it)
            })
            errorGetProfile.observe(this@HomeActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    override fun onClickProfile() {
        ProfileActivity.startActivity(this)
    }

    override fun onClickAddMedicalRecord() {

    }

    override fun onClickItem(data: MedicalRecord) {

    }
}
