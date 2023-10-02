package com.upaep.upaeppersonal.model.entities.features.home

import com.google.gson.annotations.SerializedName

data class Feature(
    @SerializedName("aplicacionComponenteId")
    val featureId: String,
    @SerializedName("nombre")
    val featureName: String,
    @SerializedName("orden")
    val order: Int,
    @SerializedName("iconoimagen")
    val featureIcon: String
)
