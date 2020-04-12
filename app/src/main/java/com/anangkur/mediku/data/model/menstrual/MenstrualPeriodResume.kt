package com.anangkur.mediku.data.model.menstrual

data class MenstrualPeriodResume(
    val year: String,
    val month: String,
    val firstDayPeriod: String,
    val lastDayPeriod: String,
    val firstDayFertile: String,
    val lastDayFertile: String,
    val firstDayFertileDay: Int,
    val lastDayFertileDay: Int,
    val longCycle: Int,
    val longPeriod: Int
)