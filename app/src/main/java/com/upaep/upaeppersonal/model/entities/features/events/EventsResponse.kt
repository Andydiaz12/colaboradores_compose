package com.upaep.upaeppersonal.model.entities.features.events

import androidx.room.PrimaryKey
import androidx.room.TypeConverters

data class EventsResponse(
    @PrimaryKey
    var idEventApp: String,
    var idActivity: Int? = null,
    var eventParent: EventParent? = null,
    var activityName: String? = null,
    var activityStartTime: String? = null
)
