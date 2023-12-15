package com.upaep.upaeppersonal.model.entities.features.locksmith

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IDDKeychainConverter {
    @TypeConverter
    fun fromIDDKeychainToJson(stat: IDDKeychain): String {
        return Gson().toJson(stat)
    }

    @TypeConverter
    fun toIDDKeychain(jsonImages: String): IDDKeychain {
        val notesType = object : TypeToken<IDDKeychain>() {}.type
        return Gson().fromJson(jsonImages, notesType)
    }
}