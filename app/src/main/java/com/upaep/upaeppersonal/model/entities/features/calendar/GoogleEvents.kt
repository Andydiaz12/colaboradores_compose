package com.upaep.upaeppersonal.model.entities.features.calendar

data class GoogleEvents(
    val id: Int = 0,
    var summary: String? = null,
    var startDate: String? = null,
    var endDate: String? = null,
    var startDateDesc: String ?= null,
    var endDateDesc: String ?= null
)
