package com.upaep.upaeppersonal.model.entities.features.dailymail

data class DailyMailItem(
    val categoryId: String,
    val itemId: String,
    val title: String,
    var content: String? = null
)
