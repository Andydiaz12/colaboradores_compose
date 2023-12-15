package com.upaep.upaeppersonal.model.database.locksmithdao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.upaep.upaeppersonal.model.entities.features.locksmith.Locksmith

@Dao
interface LocksmithDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocksmith(locksmith: Locksmith)

    @Query("SELECT * FROM Locksmith")
    fun getLocksmith() : List<Locksmith>

    @Query("DELETE FROM Locksmith")
    suspend fun deleteLocksmith()
}