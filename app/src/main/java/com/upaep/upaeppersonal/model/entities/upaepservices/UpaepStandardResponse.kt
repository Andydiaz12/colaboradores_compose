package com.upaep.upaeppersonal.model.entities.upaepservices

import com.google.gson.annotations.SerializedName

data class UpaepStandardResponse<T>(
    @SerializedName("CRYPTDATA")
    val data: T? = null,
    @SerializedName("statusCode")
    var statusCode: Int? = null,
    @SerializedName("error")
    var error: Boolean? = null,
    @SerializedName("message")
    var message: String? = null
)