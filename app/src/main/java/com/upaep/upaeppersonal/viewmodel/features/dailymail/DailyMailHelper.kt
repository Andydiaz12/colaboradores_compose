package com.upaep.upaeppersonal.viewmodel.features.dailymail

import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailItem

object DailyMailHelper {
    private var dailyMailItem: DailyMailItem? = null
    private var categoryId: Int? = null
    operator fun invoke(categoryId: Int) {
        this.categoryId = categoryId
    }

    fun setDailyMailItem(dailyMailItem: DailyMailItem) { this.dailyMailItem = dailyMailItem }
    fun getDailyMailItem() = this.dailyMailItem
    fun getCategoryId() = categoryId
}