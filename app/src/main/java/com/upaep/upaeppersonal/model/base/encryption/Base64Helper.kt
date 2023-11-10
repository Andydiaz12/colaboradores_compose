package com.upaep.upaeppersonal.model.base.encryption

import android.util.Base64

object Base64Helper {
    fun getBase64(str: String): String {
        return String(
            Base64.encode(
                str.toByteArray(),
                Base64.DEFAULT
            )
        )
    }

    fun decodeBase64(str: String): String {
        val decodedBytes = Base64.decode(str, Base64.DEFAULT)
        return String(decodedBytes)
    }
}