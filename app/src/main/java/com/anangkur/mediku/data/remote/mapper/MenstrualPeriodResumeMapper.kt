package com.anangkur.mediku.data.remote.mapper

import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodResume
import com.anangkur.mediku.data.remote.model.menstrual.MenstrualPeriodResumeRemoteModel

class MenstrualPeriodResumeMapper: RemoteMapper<MenstrualPeriodResumeRemoteModel?, MenstrualPeriodResume?> {

    companion object{
        private var INSTANCE: MenstrualPeriodResumeMapper? = null
        fun getInstance() = INSTANCE ?: MenstrualPeriodResumeMapper()
    }

    override fun mapFromRemote(data: MenstrualPeriodResumeRemoteModel?): MenstrualPeriodResume? {
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

    override fun mapToRemote(data: MenstrualPeriodResume?): MenstrualPeriodResumeRemoteModel? {
        return data?.let {
            MenstrualPeriodResumeRemoteModel(
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