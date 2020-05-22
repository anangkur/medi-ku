package com.anangkur.mediku.data.remote.model.menstrual

data class MenstrualPeriodResumeRemoteModel(
    val year: String = "",
    val month: String = "",
    val firstDayPeriod: String = "",
    val lastDayPeriod: String = "",
    val firstDayFertile: String = "",
    val lastDayFertile: String = "",
    val firstDayFertileDay: Int = 0,
    val lastDayFertileDay: Int = 0,
    val longCycle: Int = 0,
    val longPeriod: Int = 0,
    val isEdit: Boolean = false
)