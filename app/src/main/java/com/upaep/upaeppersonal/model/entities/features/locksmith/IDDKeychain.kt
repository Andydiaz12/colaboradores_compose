package com.upaep.upaeppersonal.model.entities.features.locksmith

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class IDDKeychain(
    @TypeConverters(AESKeychainConverter::class)
    @SerializedName("AES")
    @Embedded
    var AESKeychain: AESKeychain? = null,

    @TypeConverters(JWTKeychainConverter::class)
    @Embedded
    @SerializedName("JWT")
    var JWTKeychain: JWTKeychain? = null
)