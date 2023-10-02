package com.upaep.upaeppersonal.model.entities.features.mailbox

import androidx.room.PrimaryKey

data class MailboxSurveyType(
    @PrimaryKey
    var topicId : Int?=null,
    var name :String? = null,
    var description :String? = null,
    var anonymousPivotItemId:String?=null,
    var surveyMode: Int?=null,
    var isSatisfactionSurvey: Int?=null
)
