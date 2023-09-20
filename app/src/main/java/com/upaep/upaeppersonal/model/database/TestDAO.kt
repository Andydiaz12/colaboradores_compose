package com.upaep.upaeppersonal.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.upaep.upaeppersonal.model.base.entities.Test
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDAO {

    @Query("SELECT * FROM Test")
    fun getTest() : Flow<List<Test>>

    @Insert
    suspend fun addData(test: List<Test>)

    @Query("DELETE FROM Test")
    fun deleteData()
}