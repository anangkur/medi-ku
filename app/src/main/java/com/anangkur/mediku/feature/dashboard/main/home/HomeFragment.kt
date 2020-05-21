package com.anangkur.mediku.feature.dashboard.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.base.BaseFragment
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.databinding.FragmentHomeBinding
import com.anangkur.mediku.feature.covid.covid19.CovidActivity
import com.anangkur.mediku.feature.medicalRecords.detailMedicalRecord.DetailMedicalRecordActivity
import com.anangkur.mediku.feature.dashboard.main.home.adapter.MedicalRecordAdapter
import com.anangkur.mediku.feature.dashboard.main.home.adapter.NewsAdapter
import com.anangkur.mediku.feature.medicalRecords.listMedicalRecords.MedicalRecordsActivity
import com.anangkur.mediku.feature.mens.menstrual.MenstrualActivity
import com.anangkur.mediku.feature.originalNews.OriginalNewsActivity
import com.anangkur.mediku.util.*

class HomeFragment: BaseFragment<FragmentHomeBinding, HomeViewModel>(), HomeActionListener {

    companion object{
        fun newInstance() = HomeFragment()
    }

    override val mViewModel: HomeViewModel
        get() = obtainViewModel(HomeViewModel::class.java)

    private lateinit var medicalRecordAdapter: MedicalRecordAdapter
    private lateinit var newsAdapter: NewsAdapter

    override fun setupView(container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMedicalRecordAdapter()
        setupNewsAdapter()
        observeViewModel()
        mLayout.swipeHome.setOnRefreshListener {
            mViewModel.getUserProfile()
            mViewModel.getNews()
            mViewModel.getMedicalRecord()
            mLayout.swipeHome.isRefreshing = false
        }

        mLayout.cardCovid.cardCovid.setOnClickListener { this.onClickCovid19() }
        mLayout.layoutMenstrualPeriod.layoutMenstrualPeriod.setOnClickListener { this.onClickMenstrualPeriod() }

        mLayout.includeLayoutMenuDashboard.menuCovidCheck.setOnClickListener { this.onClickCovid19Check() }
        mLayout.includeLayoutMenuDashboard.menuCovidStat.setOnClickListener { this.onClickCovid19() }
        mLayout.includeLayoutMenuDashboard.menuMedicalRecords.setOnClickListener { this.onClickMedicalRecords() }
        mLayout.includeLayoutMenuDashboard.menuMenstrualPeriod.setOnClickListener { this.onClickMenstrualPeriod() }
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
                setupLoadingMedicalRecord(it)
            })
            successGetMedicalRecord.observe(this@HomeFragment, Observer {
                setupShowMedicalRecord(it)
            })
            errorGetMedicalRecord.observe(this@HomeFragment, Observer {
                setupErrorMedicalRecord(it)
            })
            progressGetProfile.observe(this@HomeFragment, Observer {

            })
            successGetProfile.observe(this@HomeFragment, Observer {
                mLayout.includeLayoutWelcome.tvName.text = it.name
            })
            errorGetProfile.observe(this@HomeFragment, Observer {
                requireActivity().showSnackbarLong(it)
            })
            healthNewsLive.observe(this@HomeFragment, Observer {
                when (it.status){
                    BaseResult.Status.LOADING -> { setupLoadingLatestNews(true) }
                    BaseResult.Status.SUCCESS -> {
                        setupLoadingLatestNews(false)
                        if (it.data.isNullOrEmpty()){
                            setupErrorLatestNews("There's something happen with our data.")
                        }else{
                            setupShowLatestNews(it.data)
                        }
                    }
                    BaseResult.Status.ERROR -> {
                        setupLoadingLatestNews(false)
                        setupErrorLatestNews(it.message?:"")
                    }
                }
            })
        }
    }

    private fun setupMedicalRecordAdapter(){
        medicalRecordAdapter = MedicalRecordAdapter(this)
        mLayout.includeLayoutMedicalRecords.recyclerMedicalRecord.apply {
            adapter = medicalRecordAdapter
            setupRecyclerViewLinear(requireContext(), RecyclerView.HORIZONTAL)
        }
    }

    private fun setupNewsAdapter(){
        newsAdapter = NewsAdapter(this)
        mLayout.includeLayoutNews.recyclerNews.apply {
            adapter = newsAdapter
            setupRecyclerViewLinear(requireContext(), RecyclerView.HORIZONTAL)
        }
    }

    private fun setupLoadingMedicalRecord(isLoading: Boolean){
        if (isLoading){
            mLayout.includeLayoutMedicalRecords.recyclerMedicalRecord.gone()
            mLayout.includeLayoutMedicalRecords.tvErrorMedicalRecord.gone()
            mLayout.includeLayoutMedicalRecords.pbMedicalRecord.visible()
        }else{
            mLayout.includeLayoutMedicalRecords.pbMedicalRecord.gone()
        }
    }

    private fun setupLoadingLatestNews(isLoading: Boolean){
        if ((isLoading)){
            mLayout.includeLayoutNews.recyclerNews.gone()
            mLayout.includeLayoutNews.tvErrorLatestNews.gone()
            mLayout.includeLayoutNews.pbLatestNews.visible()
        }else{
            mLayout.includeLayoutNews.pbLatestNews.gone()
        }
    }

    private fun setupErrorLatestNews(errorMessage: String){
        mLayout.includeLayoutNews.recyclerNews.gone()
        mLayout.includeLayoutNews.tvErrorLatestNews.visible()
        mLayout.includeLayoutNews.tvErrorLatestNews.text = errorMessage
    }

    private fun setupShowLatestNews(data: List<Article>){
        mLayout.includeLayoutNews.recyclerNews.visible()
        mLayout.includeLayoutNews.tvErrorLatestNews.gone()
        newsAdapter.setRecyclerData(data)
    }

    private fun setupShowMedicalRecord(data: List<MedicalRecord>){
        mLayout.includeLayoutMedicalRecords.recyclerMedicalRecord.visible()
        mLayout.includeLayoutMedicalRecords.tvErrorMedicalRecord.gone()
        medicalRecordAdapter.setRecyclerData(data)
    }

    private fun setupErrorMedicalRecord(errorMessage: String){
        mLayout.includeLayoutMedicalRecords.recyclerMedicalRecord.gone()
        mLayout.includeLayoutMedicalRecords.tvErrorMedicalRecord.visible()
        mLayout.includeLayoutMedicalRecords.tvErrorMedicalRecord.text = errorMessage
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

    override fun onClickMenstrualPeriod() {
        MenstrualActivity.startActivity(requireContext())
    }

    override fun onClickCovid19Check() {
        OriginalNewsActivity.startActivity(requireContext(), Const.URL_COVID19_CHECKUP)
    }

    override fun onClickMedicalRecords() {
        MedicalRecordsActivity.startActivity(requireContext())
    }
}