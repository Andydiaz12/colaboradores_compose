package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.features.DailyMailService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.database.dailymail.DailyMailDAO
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailCategories
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailItem
import com.upaep.upaeppersonal.model.entities.features.dailymail.MailOfDayRequestStructure
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DailyMailRepository @Inject constructor(
    private val locksmithHelper: LocksmithHelper,
    private val dailyMailService: DailyMailService,
    private val dailyMailDAO: DailyMailDAO
) {
    suspend fun getMailCategories(): UpaepStandardResponse<List<DailyMailCategories>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        val categories = dailyMailService.getMailCategories(keyChain = locksmith.data!!.apiGeneralMailofday!!)
        return withContext(Dispatchers.IO) {
            if(!categories.error) {
                dailyMailDAO.deleteDailyMailCategory()
                dailyMailDAO.addDailyMailCategory(categories = categories.data!!)
            }
            categories.copy(data = dailyMailDAO.getDailyMailCategory())
        }
    }

    suspend fun getDailyByCategory(request: MailOfDayRequestStructure) : UpaepStandardResponse<List<DailyMailItem>> {
        val locksmith = locksmithHelper()
        if(locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        return dailyMailService.getMailByCategory(keyChain = locksmith.data!!.apiGeneralMailofday!!, request = request)
    }

    suspend fun getItemDescription(item: DailyMailItem) : UpaepStandardResponse<List<DailyMailItem>>{
        val locksmith = locksmithHelper()
        if(locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        return dailyMailService.getItemDescription(keyChain = locksmith.data!!.apiGeneralMailofday!!, item = item)
    }
}