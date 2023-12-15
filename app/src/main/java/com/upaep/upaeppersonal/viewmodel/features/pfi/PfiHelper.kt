package com.upaep.upaeppersonal.viewmodel.features.pfi

object PfiHelper {
    private var categoryId: String? = null
    private var categoryName: String? = null
    operator fun invoke(categoryId: String, categoryName: String) {
        this.categoryId = categoryId
        this.categoryName = categoryName
    }

    fun getCategory() = categoryId!!
    fun getCategoryName() = categoryName!!
}