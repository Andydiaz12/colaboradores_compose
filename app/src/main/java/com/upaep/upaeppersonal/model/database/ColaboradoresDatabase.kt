package com.upaep.upaeppersonal.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.upaep.upaeppersonal.model.database.benefitsdao.BenefitsDAO
import com.upaep.upaeppersonal.model.database.dailymail.DailyMailDAO
import com.upaep.upaeppersonal.model.database.locksmithdao.LocksmithDAO
import com.upaep.upaeppersonal.model.database.menudao.PrivacyDAO
import com.upaep.upaeppersonal.model.entities.features.benefits.Benefit
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailCategories
import com.upaep.upaeppersonal.model.entities.features.locksmith.AESKeychainConverter
import com.upaep.upaeppersonal.model.entities.features.locksmith.IDDKeychainConverter
import com.upaep.upaeppersonal.model.entities.features.locksmith.JWTKeychainConverter
import com.upaep.upaeppersonal.model.entities.features.locksmith.Locksmith
import com.upaep.upaeppersonal.model.entities.features.menu.PrivacyPolicy

@Database(
    exportSchema = false,
    entities = [
        Locksmith::class,
        PrivacyPolicy::class,
        Benefit::class,
        DailyMailCategories::class
    ], version = 1
)

@TypeConverters(
    IDDKeychainConverter::class,
    AESKeychainConverter::class,
    JWTKeychainConverter::class
)

abstract class ColaboradoresDatabase : RoomDatabase() {
    abstract fun locksmithDAO(): LocksmithDAO
    abstract fun privacyDAO(): PrivacyDAO
    abstract fun benefitsDAO(): BenefitsDAO
    abstract fun dailyMailDAO(): DailyMailDAO
}