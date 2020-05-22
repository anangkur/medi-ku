package com.anangkur.mediku.feature.view.mens.menstrual

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.anangkur.mediku.databinding.ActivityMenstrualBinding
import com.anangkur.mediku.feature.mapper.MenstrualPeriodMonthlyMapper
import com.anangkur.mediku.feature.mapper.MenstrualPeriodResumeMapper
import com.anangkur.mediku.feature.model.menstrual.MenstrualPeriodMonthlyIntent
import com.anangkur.mediku.feature.model.menstrual.MenstrualPeriodResumeIntent
import com.anangkur.mediku.feature.view.mens.menstrualEdit.MenstrualEditActivity
import com.anangkur.mediku.util.*
import com.applandeo.materialcalendarview.EventDay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MenstrualActivity: BaseActivity<ActivityMenstrualBinding, MenstrualViewModel>() {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MenstrualActivity::class.java))
        }
    }

    override val mViewModel: MenstrualViewModel
        get() = obtainViewModel(MenstrualViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_menstrual_calendar)

    private val menstrualPeriodMonthlyMapper = MenstrualPeriodMonthlyMapper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        setupCalendarListener()
        mLayout.calendarMenstrual.setDate(Calendar.getInstance())
        val year = SimpleDateFormat("yyyy").format(mLayout.calendarMenstrual.currentPageDate.time)
        mViewModel.getMenstrualPeriod(year)
        showEventData(null)
    }

    override fun setupView(): ActivityMenstrualBinding {
        return ActivityMenstrualBinding.inflate(layoutInflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MenstrualEditActivity.REQ_CODE_EDIT){
            if (resultCode == MenstrualEditActivity.RES_CODE_EDIT){
                val year = SimpleDateFormat("yyyy").format(mLayout.calendarMenstrual.currentPageDate.time)
                mViewModel.getMenstrualPeriod(year)
            }else{
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_menstrual, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menu_edit_menstrual -> {
                if (mLayout.calendarMenstrual.currentPageDate.time.before(getTime())) {
                    val currentMonth = SimpleDateFormat("MMMM", Locale.US).format(mLayout.calendarMenstrual.currentPageDate.time)
                    MenstrualEditActivity.startActivity(this, this, mViewModel.getMenstrualResumeDataCurrentMonth(currentMonth))
                }else{
                    val currentMonth = SimpleDateFormat("MMMM yyyy", Locale.US).format(getTime())
                    showSnackbarLong(getString(R.string.error_edit_after_today, currentMonth))
                }
                true
            }
            else -> false
        }
    }

    private fun observeViewModel(){
        mViewModel.apply {
            progressGetMenstrualRecord.observe(this@MenstrualActivity, Observer {
                setupLoadingMenstrual(it)
            })
            successGetMenstrualRecord.observe(this@MenstrualActivity, Observer {
                if (activeMonth == 0){
                    MenstrualEditActivity.startActivity(this@MenstrualActivity, this@MenstrualActivity)
                }else{
                    setupMenstrualMonthly(it)
                }
            })
            errorGetMenstrualRecord.observe(this@MenstrualActivity, Observer {
                showSnackbarLong(it)
            })
            progressAddMenstrualRecord.observe(this@MenstrualActivity, Observer {

            })
            successAddMenstrualRecord.observe(this@MenstrualActivity, Observer {

            })
            errorAddMenstrualRecord.observe(this@MenstrualActivity, Observer {

            })
        }
    }

    private fun setupLoadingMenstrual(isLoading: Boolean){
        if (isLoading){
            mLayout.layoutIllustrationMenstrual.gone()
            mLayout.pbMenstrual.visible()
        }else{
            mLayout.pbMenstrual.gone()
        }
    }

    private fun setupMenstrualMonthly(data: MenstrualPeriodMonthlyIntent){
        mViewModel.activeYearData = data
        when (SimpleDateFormat("MMMM", Locale.US).format(mLayout.calendarMenstrual.currentPageDate.time)) {
            Const.KEY_JAN -> showDataCalendar(mViewModel.activeYearData?.jan, mViewModel.activeYearData?.feb, null)
            Const.KEY_FEB -> showDataCalendar(mViewModel.activeYearData?.feb, mViewModel.activeYearData?.mar, mViewModel.activeYearData?.jan)
            Const.KEY_MAR -> showDataCalendar(mViewModel.activeYearData?.mar, mViewModel.activeYearData?.apr, mViewModel.activeYearData?.feb)
            Const.KEY_APR -> showDataCalendar(mViewModel.activeYearData?.apr, mViewModel.activeYearData?.may, mViewModel.activeYearData?.mar)
            Const.KEY_MAY -> showDataCalendar(mViewModel.activeYearData?.may, mViewModel.activeYearData?.jun, mViewModel.activeYearData?.apr)
            Const.KEY_JUN -> showDataCalendar(mViewModel.activeYearData?.jun, mViewModel.activeYearData?.jul, mViewModel.activeYearData?.may)
            Const.KEY_JUL -> showDataCalendar(mViewModel.activeYearData?.jul, mViewModel.activeYearData?.aug, mViewModel.activeYearData?.jun)
            Const.KEY_AUG -> showDataCalendar(mViewModel.activeYearData?.aug, mViewModel.activeYearData?.sep, mViewModel.activeYearData?.jul)
            Const.KEY_SEP -> showDataCalendar(mViewModel.activeYearData?.sep, mViewModel.activeYearData?.oct, mViewModel.activeYearData?.aug)
            Const.KEY_OCT -> showDataCalendar(mViewModel.activeYearData?.oct, mViewModel.activeYearData?.nov, mViewModel.activeYearData?.sep)
            Const.KEY_NOV -> showDataCalendar(mViewModel.activeYearData?.nov, mViewModel.activeYearData?.dec, mViewModel.activeYearData?.oct)
            Const.KEY_DEC -> showDataCalendar(mViewModel.activeYearData?.dec, null, mViewModel.activeYearData?.nov)
        }
    }

    private fun showDataCalendar(current: MenstrualPeriodResumeIntent?, next: MenstrualPeriodResumeIntent?, previous: MenstrualPeriodResumeIntent?){
        if (current != null){
            val calendarMenstrual = ArrayList<EventDay>()
            calendarMenstrual.addAll(showMenstrualDataCalendar(current))
            calendarMenstrual.addAll(showFertileDataCalendar(current))
            if (next != null){
                calendarMenstrual.addAll(showMenstrualDataCalendar(next))
                calendarMenstrual.addAll(showFertileDataCalendar(next))
            }
            if (previous != null){
                calendarMenstrual.addAll(showMenstrualDataCalendar(previous))
                calendarMenstrual.addAll(showFertileDataCalendar(previous))
            }
            mLayout.calendarMenstrual.setEvents(calendarMenstrual)
        }
    }

    private fun showMenstrualDataCalendar(data: MenstrualPeriodResumeIntent): ArrayList<EventDay> {
        val listEvent = ArrayList<EventDay>()
        val firstMenstrualCalendar = Calendar.getInstance()
        val lastMenstrualCalendar = Calendar.getInstance()
        val firstMenstrualDate: Date = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US).parse(data.firstDayPeriod)!!
        val lastMenstrualDate: Date = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US).parse(data.lastDayPeriod)!!
        firstMenstrualCalendar.time = firstMenstrualDate
        lastMenstrualCalendar.time = lastMenstrualDate
        while (firstMenstrualCalendar.before(lastMenstrualCalendar)){
            val eventCalendar = Calendar.getInstance()
            eventCalendar.time = firstMenstrualCalendar.time
            listEvent.add(EventDay(eventCalendar, R.drawable.ic_blood_event))
            firstMenstrualCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return listEvent
    }

    private fun showFertileDataCalendar(data: MenstrualPeriodResumeIntent): ArrayList<EventDay> {
        val listEvent = ArrayList<EventDay>()
        val firstFertileCalendar = Calendar.getInstance()
        val lastFertileCalendar = Calendar.getInstance()
        val firstFertileDate: Date = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US).parse(data.firstDayFertile)!!
        val lastFertileDate: Date = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US).parse(data.lastDayFertile)!!
        firstFertileCalendar.time = firstFertileDate
        lastFertileCalendar.time = lastFertileDate
        while (firstFertileCalendar.before(lastFertileCalendar)){
            val eventCalendar = Calendar.getInstance()
            eventCalendar.time = firstFertileCalendar.time
            listEvent.add(EventDay(eventCalendar, R.drawable.ic_baby_event))
            firstFertileCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }
        return listEvent
    }

    private fun setupCalendarListener(){
        mLayout.calendarMenstrual.apply {
            setOnForwardPageChangeListener {
                onChangeDate(currentPageDate)
            }
            setOnPreviousPageChangeListener {
                onChangeDate(currentPageDate)
            }
            setOnDayClickListener {
                showEventData(it)
            }
        }
    }

    private fun onChangeDate(currentPageDate: Calendar){
        val currentYear = SimpleDateFormat("yyyy").format(currentPageDate.time)
        val currentMonth = SimpleDateFormat("MMMM", Locale.US).format(currentPageDate.time)
        currentPageDate.add(Calendar.MONTH, 1)
        val nextMonth = SimpleDateFormat("MMMM", Locale.US).format(currentPageDate.time)
        currentPageDate.add(Calendar.MONTH, -2)
        val previousMonth = SimpleDateFormat("MMMM", Locale.US).format(currentPageDate.time)

        val menstrualResumeDataCurrentMonth = mViewModel.getMenstrualResumeDataCurrentMonth(currentMonth)
        val menstrualResumeDataNextMonth = mViewModel.getMenstrualResumeDataCurrentMonth(nextMonth)
        val menstrualResumeDataPreviousMonth = mViewModel.getMenstrualResumeDataCurrentMonth(previousMonth)

        if (menstrualResumeDataCurrentMonth != null){
            val dateFormat = SimpleDateFormat(Const.DEFAULT_DATE_FORMAT_NO_TIME, Locale.US)
            val firstDayMenstrual: Date = dateFormat.parse(menstrualResumeDataCurrentMonth.firstDayPeriod)!!
            val firstDayMenstrualCalendar = Calendar.getInstance()
            firstDayMenstrualCalendar.time = firstDayMenstrual
            firstDayMenstrualCalendar.add(Calendar.DAY_OF_MONTH, menstrualResumeDataCurrentMonth.longCycle)
            val menstrualPeriodResume = createMenstrualPeriodResume(
                firstDayMenstrualCalendar,
                mViewModel.periodLong.toString(),
                mViewModel.maxCycleLong.toString(),
                mViewModel.minCycleLong.toString(),
                menstrualResumeDataNextMonth?.isEdit?:false
            )

            showDataCalendar(menstrualResumeDataCurrentMonth, menstrualResumeDataNextMonth, menstrualResumeDataPreviousMonth)

            if (menstrualResumeDataNextMonth == null) {
                mViewModel.addMenstrualPeriod(menstrualPeriodResume, firstDayMenstrualCalendar.time)
            }else if (!menstrualPeriodResume.isEdit){
                mViewModel.addMenstrualPeriod(menstrualPeriodResume, firstDayMenstrualCalendar.time)
            }
        }
        showEventData(null)
        if (currentYear != mViewModel.activeYear){
            mViewModel.getMenstrualPeriod(currentYear)
        }
    }

    private fun showEventData(eventDay: EventDay?){
        if (eventDay != null){
            mLayout.layoutIllustrationMenstrual.visible()
            when (eventDay.imageDrawable){
                R.drawable.ic_baby_event -> showFertileData(eventDay.calendar.time)
                R.drawable.ic_blood_event -> showMenstrualData(eventDay.calendar.time)
                else -> showNormalData(eventDay.calendar.time)
            }
        }else{
            mLayout.layoutIllustrationMenstrual.gone()
        }
    }

    private fun showFertileData(date: Date){
        mLayout.layoutIllustrationMenstrual.background = ContextCompat.getDrawable(this, R.drawable.rect_gradient_green)
        mLayout.ivStatusMenstrual.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baby))
        mLayout.tvDate.setTextColor(getColor(R.color.white))
        mLayout.tvDate.text = SimpleDateFormat("MMMM dd").format(date)
        mLayout.layoutMenstrual.visible()
        mLayout.tvLabelMenstrual.text = getString(R.string.dummy_status_fertile)
    }

    private fun showMenstrualData(date: Date){
        mLayout.layoutIllustrationMenstrual.background = ContextCompat.getDrawable(this, R.drawable.rect_gradient_red)
        mLayout.ivStatusMenstrual.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_blood))
        mLayout.tvDate.setTextColor(getColor(R.color.white))
        mLayout.tvDate.text = SimpleDateFormat("MMMM dd").format(date)
        mLayout.tvLabelMenstrual.visible()
        mLayout.tvLabelMenstrual.text = getString(R.string.dummy_status_menstrual)
    }

    private fun showNormalData(date: Date){
        mLayout.layoutIllustrationMenstrual.background = ContextCompat.getDrawable(this, R.color.white)
        mLayout.ivStatusMenstrual.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_doctor))
        mLayout.tvDate.setTextColor(getColor(R.color.black))
        mLayout.tvDate.text = SimpleDateFormat("MMMM dd").format(date)
        mLayout.tvLabelMenstrual.gone()
    }
}
