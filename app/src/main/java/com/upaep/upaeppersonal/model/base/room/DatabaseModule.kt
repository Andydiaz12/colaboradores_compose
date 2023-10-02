package com.upaep.upaeppersonal.model.base.room

import android.content.Context
import androidx.room.Room
import com.upaep.upaeppersonal.model.database.ColaboradoresDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

//    @Provides
//    fun providesTest(colaboradoresDatabase: ColaboradoresDatabase): TestDAO {
//        return colaboradoresDatabase.testDAO()
//    }

    @Provides
    @Singleton
    fun provideColegiosDatabase(@ApplicationContext appContext: Context): ColaboradoresDatabase {
        return Room.databaseBuilder(appContext, ColaboradoresDatabase::class.java, "ColaboradoresDatabase")
            .fallbackToDestructiveMigration()
            .build()
    }
}