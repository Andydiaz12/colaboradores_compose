package com.upaep.upaeppersonal.model.entities.features.menu

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PrivacyPolicy(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val privacyPolicy: String?
)
