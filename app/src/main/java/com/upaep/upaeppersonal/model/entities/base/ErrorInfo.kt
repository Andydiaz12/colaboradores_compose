package com.upaep.upaeppersonal.model.entities.base

data class ErrorInfo(
    val error: Boolean = false,
    var visible: Boolean,
    val message: String? = null,
)
