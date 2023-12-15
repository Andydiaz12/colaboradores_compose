package com.upaep.upaeppersonal.model.entities.features.pfi

data class PfiCategoryResponse(
    var courseId: Int? = null,
    var categoryId: Int? = null,
    var courseClave: String? = null,
    var type: String? = null,
    var modality: String? = null,
    var dates: String? = null,
    var schedules: String? = null,
    var purpose: String? = null,
    var contact: String? = null,
    var externalAudience: String? = null,
    var audience: String? = null,
    var instructor: String? = null,
    var title: String? = null
)
