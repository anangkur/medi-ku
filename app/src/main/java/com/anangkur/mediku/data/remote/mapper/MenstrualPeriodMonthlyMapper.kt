package com.anangkur.mediku.data.remote.mapper

import com.anangkur.mediku.data.model.menstrual.MenstrualPeriodMonthly
import com.anangkur.mediku.data.remote.model.menstrual.MenstrualPeriodMonthlyRemoteModel

class MenstrualPeriodMonthlyMapper(private val mapper: MenstrualPeriodResumeMapper): RemoteMapper<MenstrualPeriodMonthlyRemoteModel, MenstrualPeriodMonthly> {

    companion object{
        private var INSTANCE: MenstrualPeriodMonthlyMapper? = null
        fun getInstance() = INSTANCE ?: MenstrualPeriodMonthlyMapper(MenstrualPeriodResumeMapper.getInstance())
    }


    override fun mapFromRemote(data: MenstrualPeriodMonthlyRemoteModel): MenstrualPeriodMonthly {
        return MenstrualPeriodMonthly(
            jan = mapper.mapFromRemote(data.jan),
            feb = mapper.mapFromRemote(data.feb),
            mar = mapper.mapFromRemote(data.mar),
            apr = mapper.mapFromRemote(data.apr),
            may = mapper.mapFromRemote(data.may),
            jun = mapper.mapFromRemote(data.jun),
            jul = mapper.mapFromRemote(data.jul),
            aug = mapper.mapFromRemote(data.aug),
            sep = mapper.mapFromRemote(data.sep),
            oct = mapper.mapFromRemote(data.oct),
            nov = mapper.mapFromRemote(data.nov),
            dec = mapper.mapFromRemote(data.dec)
        )
    }

    override fun mapToRemote(data: MenstrualPeriodMonthly): MenstrualPeriodMonthlyRemoteModel {
        return MenstrualPeriodMonthlyRemoteModel(
            jan = mapper.mapToRemote(data.jan),
            feb = mapper.mapToRemote(data.feb),
            mar = mapper.mapToRemote(data.mar),
            apr = mapper.mapToRemote(data.apr),
            may = mapper.mapToRemote(data.may),
            jun = mapper.mapToRemote(data.jun),
            jul = mapper.mapToRemote(data.jul),
            aug = mapper.mapToRemote(data.aug),
            sep = mapper.mapToRemote(data.sep),
            oct = mapper.mapToRemote(data.oct),
            nov = mapper.mapToRemote(data.nov),
            dec = mapper.mapToRemote(data.dec)
        )
    }
}