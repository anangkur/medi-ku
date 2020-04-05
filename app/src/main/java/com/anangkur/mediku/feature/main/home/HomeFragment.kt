package com.anangkur.mediku.feature.main.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseFragment
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.feature.covid19.CovidActivity
import com.anangkur.mediku.feature.detailMedicalRecord.DetailMedicalRecordActivity
import com.anangkur.mediku.feature.main.home.adapter.MedicalRecordAdapter
import com.anangkur.mediku.feature.main.home.adapter.NewsAdapter
import com.anangkur.mediku.feature.originalNews.OriginalNewsActivity
import com.anangkur.mediku.util.obtainViewModel
import com.anangkur.mediku.util.setupRecyclerViewLinear
import com.anangkur.mediku.util.showSnackbarLong
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: BaseFragment<HomeViewModel>(), HomeActionListener {

    companion object{
        fun newInstance() = HomeFragment()
    }

    override val mLayout: Int
        get() = R.layout.fragment_home
    override val mViewModel: HomeViewModel
        get() = obtainViewModel(HomeViewModel::class.java)

    private lateinit var medicalRecordAdapter: MedicalRecordAdapter
    private lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMedicalRecordAdapter()
        setupNewsAdapter()
        observeViewModel()
        swipe_home.setOnRefreshListener {
            mViewModel.getUserProfile()
            mViewModel.getNews()
            mViewModel.getMedicalRecord()
        }
        card_covid.setOnClickListener { this.onClickCovid19() }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getUserProfile()
        mViewModel.getNews()
        mViewModel.getMedicalRecord()
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetMedicalRecord.observe(this@HomeFragment, Observer {
                swipe_home.isRefreshing = it
            })
            successGetMedicalRecord.observe(this@HomeFragment, Observer {
                medicalRecordAdapter.setRecyclerData(it)
            })
            errorGetMedicalRecord.observe(this@HomeFragment, Observer {
                requireActivity().showSnackbarLong(it)
            })
            progressGetProfile.observe(this@HomeFragment, Observer {

            })
            successGetProfile.observe(this@HomeFragment, Observer {
                tv_name.text = it.name
            })
            errorGetProfile.observe(this@HomeFragment, Observer {
                requireActivity().showSnackbarLong(it)
            })
            healthNewsLive.observe(this@HomeFragment, Observer {
                when (it.status){
                    BaseResult.Status.LOADING -> { swipe_home.isRefreshing = true }
                    BaseResult.Status.SUCCESS -> {
                        swipe_home.isRefreshing = false
                        newsAdapter.setRecyclerData(it.data?: listOf())
                    }
                    BaseResult.Status.ERROR -> {
                        swipe_home.isRefreshing = false
                        requireActivity().showSnackbarLong(it.message?:"")
                    }
                }
            })
        }
    }

    private fun setupMedicalRecordAdapter(){
        medicalRecordAdapter = MedicalRecordAdapter(this)
        recycler_medical_record.apply {
            adapter = medicalRecordAdapter
            setupRecyclerViewLinear(requireContext(), RecyclerView.HORIZONTAL)
        }
    }

    private fun setupNewsAdapter(){
        newsAdapter = NewsAdapter(this)
        recycler_news.apply {
            adapter = newsAdapter
            setupRecyclerViewLinear(requireContext(), RecyclerView.HORIZONTAL)
        }
    }

    override fun onClickCovid19() {
        CovidActivity.startActivity(requireContext())
    }

    override fun onClickNews(data: Article) {
        OriginalNewsActivity.startActivity(requireContext(), data.url?:"")
    }

    override fun onClickMedicalRecord(data: MedicalRecord) {
        DetailMedicalRecordActivity.startActivity(requireContext(), data)
    }
}