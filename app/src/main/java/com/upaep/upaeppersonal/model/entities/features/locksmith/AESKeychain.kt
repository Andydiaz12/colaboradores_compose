package com.upaep.upaeppersonal.model.entities.features.locksmith

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class AESKeychain(
    @SerializedName("AES_BLOCKSIZE")
    var blockSize: Int? = 256,

    @SerializedName("AES_KEY")
    var key: String? = null,

    @SerializedName("AES_INPUT_KEY")
    var inputKey: String? = null,

    @SerializedName("AES_IV")
    var iv: String? = null
)
