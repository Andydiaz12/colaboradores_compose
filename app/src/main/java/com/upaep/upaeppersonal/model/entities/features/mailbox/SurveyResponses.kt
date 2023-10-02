package com.upaep.upaeppersonal.model.entities.features.mailbox

data class SurveyResponses(
    var itemId: Int? = null,
    var componentType: Int? = null,
    var responseValue: String? = null,
    var required: Boolean? = false
)
