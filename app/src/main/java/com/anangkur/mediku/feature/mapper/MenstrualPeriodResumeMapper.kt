package com.anangkur.mediku.feature.mapper

import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.feature.model.menstrual.MenstrualPeriodResumeIntent

class MenstrualPeriodResumeMapper: IntentMapper<MenstrualPeriodResumeIntent?, MenstrualPeriodResume?> {

    companion object{
        private var INSTANCE: MenstrualPeriodResumeMapper? = null
        fun getInstance() = INSTANCE ?: MenstrualPeriodResumeMapper()
    }

    override fun mapFromIntent(data: MenstrualPeriodResumeIntent?): MenstrualPeriodResume? {
        return data?.let {
            MenstrualPeriodResume(
                year = data.year,
                isEdit = data.isEdit,
                firstDayFertile = data.firstDayFertile,
                firstDayFertileDay = data.firstDayFertileDay,
                firstDayPeriod = data.firstDayPeriod,
                lastDayFertile = data.lastDayFertile,
                lastDayFertileDay = data.lastDayFertileDay,
                lastDayPeriod = data.lastDayPeriod,
                longCycle = data.longCycle,
                longPeriod = data.longPeriod,
                month = data.month
            )
        }
    }

    override fun mapToIntent(data: MenstrualPeriodResume?): MenstrualPeriodResumeIntent? {
        return data?.let {
            MenstrualPeriodResumeIntent(
                year = data.year,
                isEdit = data.isEdit,
                firstDayFertile = data.firstDayFertile,
                firstDayFertileDay = data.firstDayFertileDay,
                firstDayPeriod = data.firstDayPeriod,
                lastDayFertile = data.lastDayFertile,
                lastDayFertileDay = data.lastDayFertileDay,
                lastDayPeriod = data.lastDayPeriod,
                longCycle = data.longCycle,
                longPeriod = data.longPeriod,
                month = data.month
            )
        }
    }
}