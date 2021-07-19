package com.anangkur.mediku.feature.view.mens.menstrualEdit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.annimon.stream.Stream
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import com.anangkur.mediku.databinding.ActivityMenstrualEditBinding
import com.anangkur.mediku.feature.mapper.MenstrualPeriodResumeMapper
import com.anangkur.mediku.feature.model.menstrual.MenstrualPeriodResumeIntent
import com.anangkur.mediku.util.*

class MenstrualEditActivity: BaseActivity<ActivityMenstrualEditBinding, MenstrualEditViewModel>(), MenstrualEditActionListener {

    companion object{
        const val REQ_CODE_EDIT = 100
        const val RES_CODE_EDIT = 200
        const val EXTRA_MENSTRUAL_RESUME = "EXTRA_MENSTRUAL_RESUME"
        fun startActivity(activity: AppCompatActivity, context: Context){
            activity.startActivityForResult(Intent(context, MenstrualEditActivity::class.java), REQ_CODE_EDIT)
        }
        fun startActivity(activity: AppCompatActivity, context: Context, data: MenstrualPeriodResumeIntent?){
            activity.startActivityForResult(Intent(context, MenstrualEditActivity::class.java)
                .putExtra(EXTRA_MENSTRUAL_RESUME, data), REQ_CODE_EDIT)
        }
    }

    override val mViewModel: MenstrualEditViewModel
        get() = obtainViewModel(MenstrualEditViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = mLayout.toolbar.toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_edit_menstrual)

    private val menstrualPeriodResumeMapper = MenstrualPeriodResumeMapper.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataIntent()
        observeViewModel()
        mLayout.etLastPeriod.setOnClickListener { this.onClickLastPeriod() }
        mLayout.btnSave.setOnClickListener { this.onCLickSave(
            lastPeriod = mLayout.etLastPeriod.text.toString(),
            periodLong = mLayout.etLongPeriod.text.toString(),
            cycleLong = mLayout.etLongCycle.text.toString(),
            isEdit = mViewModel.isEdit)
        }
    }

    override fun setupView(): ActivityMenstrualEditBinding {
        return ActivityMenstrualEditBinding.inflate(layoutInflater)
    }

    private fun getDataIntent(){
        if (intent.hasExtra(EXTRA_MENSTRUAL_RESUME)) {
            mViewModel.menstrualIntentLive.postValue(intent.getParcelableExtra(EXTRA_MENSTRUAL_RESUME))
        }
    }

    fun observeViewModel(){
        mViewModel.apply {
            progressAddMenstrualRecord.observe(this@MenstrualEditActivity, Observer {
                setupLoading(it)
            })
            successAddMenstrualRecord.observe(this@MenstrualEditActivity, Observer {
                showToastShort(getString(R.string.message_success_save_menstrual_period))
                setResult(RES_CODE_EDIT)
                finish()
            })
            errorAddMenstrualRecord.observe(this@MenstrualEditActivity, Observer {
                showSnackbarLong(it)
            })
            menstrualIntentLive.observe(this@MenstrualEditActivity, Observer {
                if (it != null) {
                    isEdit = true
                    setupMenstrualResumeToView(it)
                }
            })
        }
    }

    override fun onClickLastPeriod() {
        val maximumDate = Calendar.getInstance()
        val builder = DatePickerBuilder(this, OnSelectDateListener {
            Stream.of(it).forEach { calendar ->
                mViewModel.selectedCalendar = calendar
                val dateShow = SimpleDateFormat(Const.DATE_ENGLISH_YYYY_MM_DD, Locale.US).format(calendar.time)
                mLayout.etLastPeriod.setText(dateShow)
            }
        })
            .pickerType(CalendarView.ONE_DAY_PICKER)
            .maximumDate(maximumDate)
            .headerColor(R.color.white)
            .headerLabelColor(R.color.colorPrimary)
            .selectionColor(R.color.colorPrimary)
            .todayLabelColor(R.color.colorPrimary)
            .dialogButtonsColor(R.color.colorPrimary)

        val datePicker = builder.build()
        datePicker.show()
    }

    override fun onCLickSave(lastPeriod: String?, periodLong: String?, cycleLong: String?, isEdit: Boolean) {
        validateInput(lastPeriod, periodLong, cycleLong, isEdit)
    }

    private fun validateInput(lastPeriod: String?, periodLong: String?, cycleLong: String?, isEdit: Boolean){
        when {
            lastPeriod.isNullOrEmpty() -> { mLayout.tilLastPeriod.setErrorMessage(getString(R.string.error_last_period_empty)) }
            periodLong.isNullOrEmpty() -> { mLayout.tilLongPeriod.setErrorMessage(getString(R.string.error_period_long_empty)) }
            cycleLong.isNullOrEmpty() -> { mLayout.tilLongCycle.setErrorMessage(getString(R.string.error_cycle_long_empty)) }
            periodLong.toInt() < 1 -> { mLayout.tilLongPeriod.setErrorMessage(getString(R.string.error_period_long_less_than_1)) }
            periodLong.toInt() > 12 -> { mLayout.tilLongPeriod.setErrorMessage(getString(R.string.error_period_long_more_than_12)) }
            cycleLong.toInt() < 21 -> { mLayout.tilLongCycle.setErrorMessage(getString(R.string.error_cycle_long_less_than_21)) }
            cycleLong.toInt() > 100 -> { mLayout.tilLongCycle.setErrorMessage(getString(R.string.error_cycle_long_more_than_100)) }
            else -> { mViewModel.addMenstrualPeriod(createMenstrualPeriodResume(
                selectedCalendar = mViewModel.selectedCalendar,
                periodLong = periodLong,
                maxCycleLong = cycleLong,
                minCycleLong = cycleLong,
                isEdit = isEdit
            )) }
        }
    }

    private fun setupMenstrualResumeToView(data: MenstrualPeriodResumeIntent){
        mLayout.etLastPeriod.setText(data.firstDayPeriod)
        mLayout.etLongCycle.setText(data.longCycle.toString())
        mLayout.etLongPeriod.setText(data.longPeriod.toString())
    }

    private fun setupLoading(isLoading: Boolean){
        if (isLoading){
            mLayout.btnSave.showProgress()
        }else{
            mLayout.btnSave.hideProgress()
        }
    }
}
