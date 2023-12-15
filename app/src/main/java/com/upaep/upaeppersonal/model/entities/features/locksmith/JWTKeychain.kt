package com.upaep.upaeppersonal.model.entities.features.locksmith

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class JWTKeychain(
    @SerializedName("API_KEY")
    var apiKey: String? = null,

    @SerializedName("JWT_KEY")
    var jwtKey: String? = null,

    @SerializedName("JSON_KEY")
    var jsonKey: String? = null,

    @SerializedName("client_id")
    var clientId: String? = null,

    @SerializedName("client_secret")
    var clientSecret: String? = null
)
