package com.upaep.upaeppersonal.model.repository.features

import com.upaep.upaeppersonal.model.api.features.MailboxService
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxFormResponse
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxSurveyType
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import javax.inject.Inject

class MailboxRepository @Inject constructor(private val mailboxService: MailboxService) {
    suspend fun getMailboxOptions(): UpaepStandardResponse<List<MailboxSurveyType>> {
        return mailboxService.getMailboxOptions()
    }

    suspend fun getMailboxSurvey(surveyId: Int?) : UpaepStandardResponse<List<MailboxFormResponse>> {
        return mailboxService.getMailboxSurvey(surveyId = surveyId)
    }
}