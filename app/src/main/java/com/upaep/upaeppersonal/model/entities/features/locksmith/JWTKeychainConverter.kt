package com.upaep.upaeppersonal.model.entities.features.locksmith

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JWTKeychainConverter {
    @TypeConverter
    fun fromAESKeychainToJson(stat: JWTKeychain): String {
        return Gson().toJson(stat)
    }

    @TypeConverter
    fun toAESKeychain(jsonImages: String): JWTKeychain {
        val notesType = object : TypeToken<JWTKeychain>() {}.type
        return Gson().fromJson(jsonImages, notesType)
    }
}