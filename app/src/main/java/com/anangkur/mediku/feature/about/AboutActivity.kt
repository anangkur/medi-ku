package com.anangkur.mediku.feature.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.BuildConfig
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.about.Resource
import com.anangkur.mediku.databinding.ActivityAboutBinding
import com.anangkur.mediku.feature.about.resource.ResourceParentAdapter
import com.anangkur.mediku.util.obtainViewModel
import com.anangkur.mediku.util.openBrowser
import com.anangkur.mediku.util.setupRecyclerViewLinear

class AboutActivity: BaseActivity<ActivityAboutBinding, AboutViewModel>(), AboutActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, AboutActivity::class.java))
        }
    }

    override val mViewModel: AboutViewModel
        get() = obtainViewModel(AboutViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_about)

    private lateinit var mParentAdapter: ResourceParentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()
        observeViewModel()
        mViewModel.createResourceData()
        mLayout.tvVersionNumber.text = BuildConfig.VERSION_NAME
        mLayout.tvCovid19Data.setOnClickListener { this.onClickCovid19Api("https://covid19api.com") }
        mLayout.tvNewsData.setOnClickListener { this.onClickNewsApi("https://newsapi.org") }
        mLayout.btnRate.setOnClickListener { this.onClickGiveRating("https://play.google.com/store/apps/details?id=com.anangkur.mediku") }
    }

    override fun setupView(): ActivityAboutBinding {
        return ActivityAboutBinding.inflate(layoutInflater)
    }

    private fun setupAdapter(){
        mParentAdapter = ResourceParentAdapter(this)
        mLayout.recyclerResource.apply {
            adapter = mParentAdapter
            setupRecyclerViewLinear(this@AboutActivity, RecyclerView.VERTICAL)
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            listResourceLive.observe(this@AboutActivity, Observer {
                mParentAdapter.setRecyclerData(it)
            })
        }
    }

    override fun onClickResourceParent(resourceParent: Resource) {
        openBrowser(resourceParent.link)
    }

    override fun onClickResourceChild(resourceChild: Resource.ResourceChild) {
        openBrowser(resourceChild.link)
    }

    override fun onClickCovid19Api(url: String) {
        openBrowser(url)
    }

    override fun onClickNewsApi(url: String) {
        openBrowser(url)
    }

    override fun onClickGiveRating(url: String) {
        openBrowser(url)
    }
}
