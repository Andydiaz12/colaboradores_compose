package com.upaep.upaeppersonal.model.entities.features.mailbox

import androidx.room.PrimaryKey
import androidx.room.TypeConverters

data class MailboxFormResponse(
//    @PrimaryKey
    var itemId: Int? = null,
    var topicId: Int? = null,
    var componentType: Int? = null,
    var title: String? = null,
    var required: Boolean = false,
    var order: Int? = null,
    var componentName: String? = null,
    var description: String? = null,
    var indication: String? = null,
    var childs: List<MailboxFormResponse>
)
