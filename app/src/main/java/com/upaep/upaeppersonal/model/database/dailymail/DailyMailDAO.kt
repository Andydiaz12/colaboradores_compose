package com.upaep.upaeppersonal.model.database.dailymail

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailCategories

@Dao
interface DailyMailDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDailyMailCategory(categories: List<DailyMailCategories>)

    @Query("DELETE FROM DailyMailCategories")
    suspend fun deleteDailyMailCategory()

    @Query("SELECT * FROM DailyMailCategories")
    suspend fun getDailyMailCategory() : List<DailyMailCategories>
}