package com.anangkur.mediku.feature.covid.covid19

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.BaseResult
import com.anangkur.mediku.data.model.newCovid19.NewCovid19Summary
import com.anangkur.mediku.databinding.ActivityCovidBinding
import com.anangkur.mediku.feature.covid.covid19.adapter.CovidHorizontalAdapter
import com.anangkur.mediku.feature.covid.covid19.adapter.CovidVerticalAdapter
import com.anangkur.mediku.util.*
import java.text.SimpleDateFormat
import java.util.*

class CovidActivity : BaseActivity<ActivityCovidBinding, CovidViewModel>() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, CovidActivity::class.java))
        }
    }

    override val mViewModel: CovidViewModel
        get() = obtainViewModel(CovidViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_covid_19)

    private lateinit var otherCountryAdapter: CovidVerticalAdapter
    private lateinit var yourCountryAdapter: CovidHorizontalAdapter
    private lateinit var topCountryAdapter: CovidHorizontalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupOtherCountryAdapter()
        setupYourCountryAdapter()
        setupTopCountryAdapter()
        observeViewModel()
        mViewModel.getCovid19Data()
        mLayout.swipeCovid.setOnRefreshListener {
            mViewModel.getCovid19Data()
            mLayout.swipeCovid.isRefreshing = false
        }
    }

    override fun setupView(): ActivityCovidBinding {
        return ActivityCovidBinding.inflate(layoutInflater)
    }

    private fun observeViewModel(){
        mViewModel.apply {
            covidLiveData.observe(this@CovidActivity, Observer {
                when (it.status){
                    BaseResult.Status.LOADING -> {
                        setupLoadingGeneral(true)
                    }
                    BaseResult.Status.ERROR -> {
                        setupLoadingGeneral(false)
                        showSnackbarLong(it.message?:"")
                    }
                    BaseResult.Status.SUCCESS -> {
                        setupLoadingGeneral(false)
                        setupShownDate(it.data?.get(0)?.date)
                        mLayout.tvDataShown.visible()
                        setupDataOtherCountryToView(it.data!!.subList(1, it.data.size))
                        getCovid19DataOnYourCountry(country.convertCoutryCodeIsoToCountryName())
                        getCovid19DataTopCountry()
                    }
                }
            })
            covid19DataOnYourCountryLive.observe(this@CovidActivity, Observer {
                when (it.status){
                    BaseResult.Status.LOADING -> {
                        setupLoadingYourCountry(true)
                    }
                    BaseResult.Status.SUCCESS -> {
                        setupLoadingYourCountry(false)
                        yourCountryAdapter.setRecyclerData(it.data!!)
                    }
                    BaseResult.Status.ERROR -> {
                        setupLoadingYourCountry(false)
                        showSnackbarLong(it.message?:"")
                    }
                }
            })
            covid19DataTopCountry.observe(this@CovidActivity, Observer {
                when (it.status){
                    BaseResult.Status.LOADING -> {
                        setupLoadingTopCountry(true)
                    }
                    BaseResult.Status.SUCCESS -> {
                        setupLoadingTopCountry(false)
                        topCountryAdapter.setRecyclerData(it.data!!)
                    }
                    BaseResult.Status.ERROR -> {
                        setupLoadingTopCountry(false)
                        showSnackbarLong(it.message?:"")
                    }
                }
            })
        }
    }

    private fun setupOtherCountryAdapter(){
        otherCountryAdapter = CovidVerticalAdapter()
        mLayout.recyclerOtherCountry.apply {
            adapter = otherCountryAdapter
            setupRecyclerViewLinear(this@CovidActivity, RecyclerView.VERTICAL)
        }
    }

    private fun setupYourCountryAdapter(){
        yourCountryAdapter = CovidHorizontalAdapter()
        mLayout.recyclerYourCountry.apply {
            adapter = yourCountryAdapter
            setupRecyclerViewLinear(this@CovidActivity, RecyclerView.HORIZONTAL)
        }
    }

    private fun setupTopCountryAdapter(){
        topCountryAdapter = CovidHorizontalAdapter()
        mLayout.recyclerTopCountry.apply {
            adapter = topCountryAdapter
            setupRecyclerViewLinear(this@CovidActivity, RecyclerView.HORIZONTAL)
        }
    }

    private fun setupDataOtherCountryToView(listCovid19Data: List<NewCovid19Summary>){
        otherCountryAdapter.setRecyclerData(listCovid19Data)
        val statCovid = mViewModel.getStatCovid(listCovid19Data)
        mLayout.tvStatConfirmed.text = statCovid.first.formatThousandNumber()
        mLayout.tvStatDeath.text = statCovid.second.formatThousandNumber()
        mLayout.tvStatRecover.text = statCovid.third.formatThousandNumber()
    }

    private fun setupLoadingGeneral(isLoading: Boolean){
        if (isLoading){
            mLayout.pbStatConfirmed.visible()
            mLayout.pbStatDeath.visible()
            mLayout.pbStatRecover.visible()
            mLayout.pbLayoutContent.visible()
            mLayout.tvDataShown.gone()
            mLayout.layoutContent.gone()
            mLayout.tvLabelStatConfirmed.gone()
            mLayout.tvLabelStatDeath.gone()
            mLayout.tvLabelStatRecover.gone()
            mLayout.tvStatConfirmed.gone()
            mLayout.tvStatDeath.gone()
            mLayout.tvStatRecover.gone()
        }else{
            mLayout.pbStatConfirmed.gone()
            mLayout.pbStatDeath.gone()
            mLayout.pbStatRecover.gone()
            mLayout.pbLayoutContent.gone()
            mLayout.layoutContent.visible()
            mLayout.tvLabelStatConfirmed.visible()
            mLayout.tvLabelStatDeath.visible()
            mLayout.tvLabelStatRecover.visible()
            mLayout.tvStatConfirmed.visible()
            mLayout.tvStatDeath.visible()
            mLayout.tvStatRecover.visible()
        }
    }

    private fun setupShownDate(date: String?){
        val dateParsed = try {
            SimpleDateFormat(Const.DATE_FORMAT_NEW_COVID19, Locale.US).parse(date?:"1990-01-01T00:00:00.00000000Z")
        }catch (e: Exception){
            e.printStackTrace()
            try {
                SimpleDateFormat(Const.DATE_FORMAT_NEW_COVID19_2, Locale.US).parse(date?:"1990-01-01T00:00:00Z")
            }catch (e: Exception){
                e.printStackTrace()
                null
            }
        }
        val dateFormatted = try {
            SimpleDateFormat(Const.DATE_FORMAT_SHOWN_COVID19, Locale.US).format(dateParsed?:date)
        }catch (e: Exception){
            e.printStackTrace()
            date
        }
        mLayout.tvDataShown.text = getString(R.string.label_data_shown, dateFormatted)
    }

    private fun setupLoadingYourCountry(isLoading: Boolean){
        if (isLoading){
            mLayout.pbLayoutContent.visible()
            mLayout.layoutContent.gone()
        }else{
            mLayout.pbLayoutContent.gone()
            mLayout.layoutContent.visible()
        }
    }

    private fun setupLoadingTopCountry(isLoading: Boolean){
        if (isLoading){
            mLayout.pbLayoutContent.visible()
            mLayout.layoutContent.gone()
        }else{
            mLayout.pbLayoutContent.gone()
            mLayout.layoutContent.visible()
        }
    }
}
