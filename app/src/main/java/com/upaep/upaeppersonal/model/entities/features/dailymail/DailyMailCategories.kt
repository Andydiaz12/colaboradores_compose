package com.upaep.upaeppersonal.model.entities.features.dailymail

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DailyMailCategories(
    @PrimaryKey
    var id: Int? = null,
    var title: String? = null,
    var urlicon: String? = null
)
