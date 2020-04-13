package com.anangkur.mediku.feature.mens.menstrual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodMonthly
import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.util.gone
import com.anangkur.mediku.util.obtainViewModel
import com.anangkur.mediku.util.showSnackbarLong
import com.anangkur.mediku.util.visible
import com.applandeo.materialcalendarview.EventDay
import kotlinx.android.synthetic.main.activity_menstrual.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class MenstrualActivity: BaseActivity<MenstrualViewModel>() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MenstrualActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_menstrual
    override val mViewModel: MenstrualViewModel
        get() = obtainViewModel(MenstrualViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_menstrual_calendar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        setupCalendarListener()
        calendar_menstrual.setDate(Calendar.getInstance())
        mViewModel.getMedicalRecord(calendar_menstrual.currentPageDate.time)
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetMenstrualRecord.observe(this@MenstrualActivity, Observer {
                setupLoadingMenstrual(it)
            })
            successGetMenstrualRecord.observe(this@MenstrualActivity, Observer {
                setupMenstrualMonthly(it)
            })
            errorGetMenstrualRecord.observe(this@MenstrualActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    private fun setupLoadingMenstrual(isLoading: Boolean){
        if (isLoading){
            layout_illustration_menstrual.gone()
            pb_menstrual.visible()
        }else{
            layout_illustration_menstrual.visible()
            pb_menstrual.gone()
        }
    }

    private fun setupMenstrualMonthly(data: MenstrualPeriodMonthly){
        when {
            data.jan != null -> {showMenstrualData(data.jan!!)}
            data.feb != null -> {showMenstrualData(data.feb!!)}
            data.mar != null -> {showMenstrualData(data.mar!!)}
            data.apr != null -> {showMenstrualData(data.apr!!)}
            data.may != null -> {showMenstrualData(data.may!!)}
            data.jun != null -> {showMenstrualData(data.jun!!)}
            data.jul != null -> {showMenstrualData(data.jul!!)}
            data.aug != null -> {showMenstrualData(data.aug!!)}
            data.sep != null -> {showMenstrualData(data.sep!!)}
            data.oct != null -> {showMenstrualData(data.oct!!)}
            data.nov != null -> {showMenstrualData(data.nov!!)}
            data.dec != null -> {showMenstrualData(data.dec!!)}
        }
    }

    private fun showMenstrualData(data: MenstrualPeriodResume){
        val calendarMenstrual = ArrayList<EventDay>()

        var firstMenstrualDate: LocalDate = LocalDate.parse(data.firstDayPeriod)
        val lastMenstrualDate: LocalDate = LocalDate.parse(data.lastDayPeriod)

        var firstFertileDate: LocalDate = LocalDate.parse(data.firstDayFertile)
        val lastDayFertileDate: LocalDate = LocalDate.parse(data.lastDayFertile)!!

        while (!firstMenstrualDate.isAfter(lastMenstrualDate)){
            val calendar = Calendar.getInstance()
            calendar.set(
                firstMenstrualDate.year,
                firstMenstrualDate.monthValue-1,
                firstMenstrualDate.dayOfMonth
            )
            calendarMenstrual.add(EventDay(calendar, R.drawable.ic_blood))
            firstMenstrualDate = firstMenstrualDate.plusDays(1L)
        }

        while (!firstFertileDate.isAfter(lastDayFertileDate)){
            val calendar = Calendar.getInstance()
            calendar.set(
                firstFertileDate.year,
                firstFertileDate.monthValue-1,
                firstFertileDate.dayOfMonth
            )
            calendarMenstrual.add(EventDay(calendar, R.drawable.ic_baby))
            firstFertileDate = firstFertileDate.plusDays(1L)
        }

        calendar_menstrual.setEvents(calendarMenstrual)
    }

    private fun setupCalendarListener(){
        calendar_menstrual.setOnForwardPageChangeListener {
            mViewModel.getMedicalRecord(calendar_menstrual.currentPageDate.time)
        }
        calendar_menstrual.setOnPreviousPageChangeListener {
            mViewModel.getMedicalRecord(calendar_menstrual.currentPageDate.time)
        }
    }
}
