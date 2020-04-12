package com.anangkur.mediku.feature.mens.menstrualEdit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.anangkur.mediku.R
import com.anangkur.mediku.base.BaseActivity
import com.annimon.stream.Stream
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import kotlinx.android.synthetic.main.activity_menstrual_edit.*
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import com.anangkur.mediku.util.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class MenstrualEditActivity: BaseActivity<MenstrualEditViewModel>(), MenstrualEditActionListener {

    companion object{
        fun startActivity(context: Context){
            context.startActivity(Intent(context, MenstrualEditActivity::class.java))
        }
    }

    override val mLayout: Int
        get() = R.layout.activity_menstrual_edit
    override val mViewModel: MenstrualEditViewModel
        get() = obtainViewModel(MenstrualEditViewModel::class.java)
    override val mToolbar: Toolbar?
        get() = toolbar
    override val mTitleToolbar: String?
        get() = getString(R.string.toolbar_edit_menstrual)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeViewModel()
        et_last_period.setOnClickListener { this.onClickLastPeriod() }
        btn_save.setOnClickListener { this.onCLickSave(
            lastPeriod = et_last_period.text.toString(),
            periodLong = et_long_period.text.toString(),
            cycleLong = et_long_cycle.text.toString())
        }
    }

    fun observeViewModel(){
        mViewModel.apply {
            progressAddMenstrualRecord.observe(this@MenstrualEditActivity, Observer {
                if (it){
                    btn_save.showProgress()
                }else{
                    btn_save.hideProgress()
                }
            })
            successAddMenstrualRecord.observe(this@MenstrualEditActivity, Observer {
                showToastShort(getString(R.string.message_success_save_menstrual_period))
            })
            errorAddMenstrualRecord.observe(this@MenstrualEditActivity, Observer {
                showSnackbarLong(it)
            })
        }
    }

    override fun onClickLastPeriod() {
        val maximumDate = Calendar.getInstance()
        val builder = DatePickerBuilder(this, OnSelectDateListener {
            Stream.of(it).forEach { calendar ->
                mViewModel.selectedCalendar = calendar
                val dateShow = SimpleDateFormat(Const.DATE_ENGLISH_YYYY_MM_DD, Locale.US).format(calendar.time)
                et_last_period.setText(dateShow)
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

    override fun onCLickSave(lastPeriod: String?, periodLong: String?, cycleLong: String?) {
        validateInput(lastPeriod, periodLong, cycleLong)
    }

    private fun validateInput(lastPeriod: String?, periodLong: String?, cycleLong: String?){
        when {
            lastPeriod.isNullOrEmpty() -> { til_last_period.setErrorMessage(getString(R.string.error_last_period_empty)) }
            periodLong.isNullOrEmpty() -> { til_long_period.setErrorMessage(getString(R.string.error_period_long_empty)) }
            cycleLong.isNullOrEmpty() -> { til_long_cycle.setErrorMessage(getString(R.string.error_cycle_long_empty)) }
            periodLong.toInt() < 1 -> { til_long_period.setErrorMessage(getString(R.string.error_period_long_less_than_1)) }
            periodLong.toInt() > 12 -> { til_long_period.setErrorMessage(getString(R.string.error_period_long_more_than_12)) }
            cycleLong.toInt() < 21 -> { til_long_cycle.setErrorMessage(getString(R.string.error_cycle_long_less_than_21)) }
            cycleLong.toInt() > 100 -> { til_long_cycle.setErrorMessage(getString(R.string.error_cycle_long_more_than_100)) }
            else -> { mViewModel.addMenstrualPeriod(mViewModel.createMenstrualPeriodResume(periodLong, cycleLong)) }
        }
    }
}
