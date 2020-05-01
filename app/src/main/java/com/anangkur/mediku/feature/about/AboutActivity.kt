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
import com.anangkur.mediku.feature.about.resource.ResourceAdapter
import com.anangkur.mediku.util.obtainViewModel
import com.anangkur.mediku.util.openBrowser
import com.anangkur.mediku.util.setupRecyclerViewLinear
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class AboutActivity: BaseActivity<AboutViewModel>(), AboutActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, AboutActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_about
    override val mViewModel: AboutViewModel
        get() = obtainViewModel(AboutViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_about)

    private lateinit var mAdapter: ResourceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()
        observeViewModel()
        mViewModel.createResourceData()
        tv_version_number.text = BuildConfig.VERSION_NAME
        tv_covid_19_data.setOnClickListener { this.onClickCovid19Api("https://covid19api.com") }
        tv_news_data.setOnClickListener { this.onClickNewsApi("https://newsapi.org") }
    }

    private fun setupAdapter(){
        mAdapter = ResourceAdapter(this)
        recycler_resource.apply {
            adapter = mAdapter
            setupRecyclerViewLinear(this@AboutActivity, RecyclerView.VERTICAL)
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            listResourceLive.observe(this@AboutActivity, Observer {
                mAdapter.setRecyclerData(it)
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
}
