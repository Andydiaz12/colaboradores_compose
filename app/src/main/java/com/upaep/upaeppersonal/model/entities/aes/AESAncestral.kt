package com.upaep.upaeppersonal.model.entities.aes

import com.upaep.upaeppersonal.BuildConfig
import com.upaep.upaeppersonal.model.entities.features.locksmith.AESKeychain

object AESAncestral {
    fun getAES() : AESKeychain {
        return AESKeychain(
            blockSize = BuildConfig.LOCKSMITH_AES_BLOCK_SIZE,
            key = BuildConfig.LOCKSMITH_AES_KEY,
            iv = BuildConfig.LOCKSMITH_AES_IV,
            inputKey = null
        )
    }
}