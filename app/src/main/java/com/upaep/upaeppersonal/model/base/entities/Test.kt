package com.upaep.upaeppersonal.model.base.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Test(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val extra: String
)
