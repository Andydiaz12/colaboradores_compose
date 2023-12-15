package com.upaep.upaeppersonal.model.entities.features.locksmith

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AESKeychainConverter {
    @TypeConverter
    fun fromAESKeychainToJson(stat: AESKeychain): String {
        return Gson().toJson(stat)
    }

    @TypeConverter
    fun toAESKeychain(jsonImages: String): AESKeychain {
        val notesType = object : TypeToken<AESKeychain>() {}.type
        return Gson().fromJson(jsonImages, notesType)
    }
}