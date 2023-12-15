package com.upaep.upaeppersonal.model.base.room

import android.content.Context
import androidx.room.Room
import com.upaep.upaeppersonal.model.database.ColaboradoresDatabase
import com.upaep.upaeppersonal.model.database.benefitsdao.BenefitsDAO
import com.upaep.upaeppersonal.model.database.dailymail.DailyMailDAO
import com.upaep.upaeppersonal.model.database.locksmithdao.LocksmithDAO
import com.upaep.upaeppersonal.model.database.menudao.PrivacyDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun providesLocksmith(colaboradoresDatabase: ColaboradoresDatabase) : LocksmithDAO {
        return colaboradoresDatabase.locksmithDAO()
    }

    @Provides
    fun providesPrivacy(colaboradoresDatabase: ColaboradoresDatabase) : PrivacyDAO {
        return colaboradoresDatabase.privacyDAO()
    }

    @Provides
    fun providesBenefits(colaboradoresDatabase: ColaboradoresDatabase) : BenefitsDAO {
        return colaboradoresDatabase.benefitsDAO()
    }

    @Provides
    fun providesDailyMail(colaboradoresDatabase: ColaboradoresDatabase) : DailyMailDAO {
        return colaboradoresDatabase.dailyMailDAO()
    }

    @Provides
    @Singleton
    fun provideColegiosDatabase(@ApplicationContext appContext: Context): ColaboradoresDatabase {
        return Room.databaseBuilder(appContext, ColaboradoresDatabase::class.java, "ColaboradoresDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }
}