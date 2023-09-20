package com.upaep.upaeppersonal.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.upaep.upaeppersonal.model.base.entities.Test

@Database(
    entities = [
        Test::class
    ], version = 1
)
abstract class ColaboradoresDatabase : RoomDatabase() {
    abstract fun testDAO() : TestDAO
}