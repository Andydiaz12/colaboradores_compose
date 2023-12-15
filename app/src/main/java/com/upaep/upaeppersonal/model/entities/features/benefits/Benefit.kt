package com.upaep.upaeppersonal.model.entities.features.benefits

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Benefit(
    @PrimaryKey
    var categoryId : Int?=null,
    var name :String ?=null,
    var urlIcon :String ?=null,
    var urlIconDark :String ?=null
)
