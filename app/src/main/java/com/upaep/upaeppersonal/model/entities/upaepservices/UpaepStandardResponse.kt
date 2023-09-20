package com.upaep.upaeppersonal.model.entities.upaepservices

import com.google.gson.annotations.SerializedName

data class UpaepStandardResponse<T>(
    @SerializedName("CRYPTDATA")
    val data: T,
    val error: Boolean = true
)
