package com.anangkur.mediku.feature.mapper

import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodMonthly
import com.anangkur.mediku.feature.model.menstrual.MenstrualPeriodMonthlyIntent

class MenstrualPeriodMonthlyMapper(private val mapper: MenstrualPeriodResumeMapper): IntentMapper<MenstrualPeriodMonthlyIntent, MenstrualPeriodMonthly> {

    companion object{
        private var INSTANCE: MenstrualPeriodMonthlyMapper? = null
        fun getInstance() = INSTANCE ?: MenstrualPeriodMonthlyMapper(MenstrualPeriodResumeMapper.getInstance())
    }


    override fun mapFromIntent(data: MenstrualPeriodMonthlyIntent): MenstrualPeriodMonthly {
        return MenstrualPeriodMonthly(
            jan = mapper.mapFromIntent(data.jan),
            feb = mapper.mapFromIntent(data.feb),
            mar = mapper.mapFromIntent(data.mar),
            apr = mapper.mapFromIntent(data.apr),
            may = mapper.mapFromIntent(data.may),
            jun = mapper.mapFromIntent(data.jun),
            jul = mapper.mapFromIntent(data.jul),
            aug = mapper.mapFromIntent(data.aug),
            sep = mapper.mapFromIntent(data.sep),
            oct = mapper.mapFromIntent(data.oct),
            nov = mapper.mapFromIntent(data.nov),
            dec = mapper.mapFromIntent(data.dec)
        )
    }

    override fun mapToIntent(data: MenstrualPeriodMonthly): MenstrualPeriodMonthlyIntent {
        return MenstrualPeriodMonthlyIntent(
            jan = mapper.mapToIntent(data.jan),
            feb = mapper.mapToIntent(data.feb),
            mar = mapper.mapToIntent(data.mar),
            apr = mapper.mapToIntent(data.apr),
            may = mapper.mapToIntent(data.may),
            jun = mapper.mapToIntent(data.jun),
            jul = mapper.mapToIntent(data.jul),
            aug = mapper.mapToIntent(data.aug),
            sep = mapper.mapToIntent(data.sep),
            oct = mapper.mapToIntent(data.oct),
            nov = mapper.mapToIntent(data.nov),
            dec = mapper.mapToIntent(data.dec)
        )
    }
}