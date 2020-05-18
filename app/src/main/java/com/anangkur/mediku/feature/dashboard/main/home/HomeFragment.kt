package com.anangkur.mediku.feature.dashboard.main.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseFragment
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.medical.MedicalRecord
import com.anangkur.mediku.data.model.news.Article
import com.anangkur.mediku.feature.covid.covid19.CovidActivity
import com.anangkur.mediku.feature.medicalRecords.detailMedicalRecord.DetailMedicalRecordActivity
import com.anangkur.mediku.feature.dashboard.main.home.adapter.MedicalRecordAdapter
import com.anangkur.mediku.feature.dashboard.main.home.adapter.NewsAdapter
import com.anangkur.mediku.feature.medicalRecords.listMedicalRecords.MedicalRecordsActivity
import com.anangkur.mediku.feature.mens.menstrual.MenstrualActivity
import com.anangkur.mediku.feature.originalNews.OriginalNewsActivity
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_covid19_alert.*
import kotlinx.android.synthetic.main.layout_medical_records.*
import kotlinx.android.synthetic.main.layout_menstrual_period.*
import kotlinx.android.synthetic.main.layout_menu_dashboard.*
import kotlinx.android.synthetic.main.layout_news.*
import kotlinx.android.synthetic.main.layout_welcome.*

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
            swipe_home.isRefreshing = false
        }

        card_covid.setOnClickListener { this.onClickCovid19() }
        layout_menstrual_period.setOnClickListener { this.onClickMenstrualPeriod() }

        menu_covid_check.setOnClickListener { this.onClickCovid19Check() }
        menu_covid_stat.setOnClickListener { this.onClickCovid19() }
        menu_medical_records.setOnClickListener { this.onClickMedicalRecords() }
        menu_menstrual_period.setOnClickListener { this.onClickMenstrualPeriod() }
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
                tv_name.text = it.name
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

    private fun setupLoadingMedicalRecord(isLoading: Boolean){
        if (isLoading){
            recycler_medical_record.gone()
            tv_error_medical_record.gone()
            pb_medical_record.visible()
        }else{
            pb_medical_record.gone()
        }
    }

    private fun setupLoadingLatestNews(isLoading: Boolean){
        if ((isLoading)){
            recycler_news.gone()
            tv_error_latest_news.gone()
            pb_latest_news.visible()
        }else{
            pb_latest_news.gone()
        }
    }

    private fun setupErrorLatestNews(errorMessage: String){
        recycler_news.gone()
        tv_error_latest_news.visible()
        tv_error_latest_news.text = errorMessage
    }

    private fun setupShowLatestNews(data: List<Article>){
        recycler_news.visible()
        tv_error_latest_news.gone()
        newsAdapter.setRecyclerData(data)
    }

    private fun setupShowMedicalRecord(data: List<MedicalRecord>){
        recycler_medical_record.visible()
        tv_error_medical_record.gone()
        medicalRecordAdapter.setRecyclerData(data)
    }

    private fun setupErrorMedicalRecord(errorMessage: String){
        recycler_medical_record.gone()
        tv_error_medical_record.visible()
        tv_error_medical_record.text = errorMessage
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