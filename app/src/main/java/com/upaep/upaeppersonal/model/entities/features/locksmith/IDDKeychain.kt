package com.upaep.upaeppersonal.model.entities.features.locksmith

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class IDDKeychain(
    @SerializedName("AES")
    @Embedded
    var AESKeychain: AESKeychain? = null,

    @Embedded
    @SerializedName("JWT")
    var JWTKeychain: JWTKeychain? = null
)