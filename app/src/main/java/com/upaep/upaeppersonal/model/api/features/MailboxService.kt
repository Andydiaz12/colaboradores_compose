package com.upaep.upaeppersonal.model.api.features

import com.upaep.upaeppersonal.model.base.ColaboradoresInterface
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxFormResponse
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxSurveyType
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MailboxService @Inject constructor(private val api: ColaboradoresInterface) {
    suspend fun getMailboxOptions(): UpaepStandardResponse<List<MailboxSurveyType>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getMailboxSurveys()
                if (response.isSuccessful) {
                    UpaepStandardResponse(data = response.body()!!, error = false)
                } else {
                    UpaepStandardResponse(data = emptyList())
                }
            } catch (e: Exception) {
                UpaepStandardResponse(data = emptyList())
            }
        }
    }

    suspend fun getMailboxSurvey(surveyId: Int?): UpaepStandardResponse<List<MailboxFormResponse>> {
        return withContext(Dispatchers.IO) {
            try {
//                val response = if(surveyId == 1) api.testSurvey_1() else api.testSurvey_1()
                val response = when (surveyId) {
                    1 -> api.testSurvey_1()
                    2 -> api.testSurvey_2()
                    5 -> api.testSurvey_5()
                    6 -> api.testSurvey_6()
                    8 -> api.testSurvey_8()
                    else -> api.testSurvey_1()
                }
                if (response.isSuccessful) {
                    UpaepStandardResponse(data = response.body()!!, error = false)
                } else {
                    UpaepStandardResponse(data = emptyList())
                }
            } catch (e: Exception) {
                UpaepStandardResponse(data = emptyList())
            }
        }
    }
}