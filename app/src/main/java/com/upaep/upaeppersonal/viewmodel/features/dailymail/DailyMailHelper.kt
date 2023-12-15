package com.upaep.upaeppersonal.viewmodel.features.dailymail

object DailyMailHelper {
    private var categoryId: Int? = null
    operator fun invoke(categoryId: Int) {
        this.categoryId = categoryId
    }

    fun getCategoryId() = categoryId
}