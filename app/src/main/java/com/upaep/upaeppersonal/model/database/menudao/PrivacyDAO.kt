package com.upaep.upaeppersonal.model.database.menudao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upaep.upaeppersonal.model.entities.features.menu.PrivacyPolicy

@Dao
interface PrivacyDAO {
    @Query("SELECT * FROM PrivacyPolicy")
    suspend fun getPrivacyPolicy() : PrivacyPolicy?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setPrivacyPolicy(privacy: PrivacyPolicy)

    @Query("DELETE FROM PrivacyPolicy")
    suspend fun deletePrivacyPolicy()
}