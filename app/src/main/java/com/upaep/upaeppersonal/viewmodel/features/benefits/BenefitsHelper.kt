package com.upaep.upaeppersonal.viewmodel.features.benefits

object BenefitsHelper {
    private var categoryId: Int? = null
    operator fun invoke(categoryId: Int) {
        this.categoryId = categoryId
    }

    fun getCategory(): Int = this.categoryId ?: -1
}