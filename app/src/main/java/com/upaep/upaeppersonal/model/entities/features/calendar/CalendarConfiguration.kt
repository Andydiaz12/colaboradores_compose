package com.upaep.upaeppersonal.model.entities.features.calendar

data class CalendarConfiguration(
    val numRows: Int,
    val daysMatrix: Array<IntArray>,
    val startDay: Int,
    val monthNumber: Int,
    val monthName: String,
    var monthInInt: Int,
    var yearInInt: Int
)
