package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.features.MailboxService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxFormGet
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxFormResponse
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxSurveyType
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import javax.inject.Inject

class MailboxRepository @Inject constructor(
    private val mailboxService: MailboxService,
    private val locksmithHelper: LocksmithHelper
) {
    suspend fun getMailboxOptions(): UpaepStandardResponse<List<MailboxSurveyType>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        return mailboxService.getMailboxOptions(locksmith.data!!.generalMovilKeychain!!)
    }

    suspend fun getMailboxSurvey(mailboxFormGet: MailboxFormGet): UpaepStandardResponse<List<MailboxFormResponse>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        return mailboxService.getMailboxSurvey(
            mailboxFormGet = mailboxFormGet,
            iddKeychain = locksmith.data!!.generalMovilKeychain!!
        )
    }
}