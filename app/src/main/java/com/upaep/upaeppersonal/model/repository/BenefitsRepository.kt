package com.upaep.upaeppersonal.model.repository

import android.util.Log
import com.upaep.upaeppersonal.model.api.features.BenefitsService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.database.benefitsdao.BenefitsDAO
import com.upaep.upaeppersonal.model.entities.features.benefits.Benefit
import com.upaep.upaeppersonal.model.entities.features.benefits.BenefitCategory
import com.upaep.upaeppersonal.model.entities.features.benefits.BenefitDetail
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BenefitsRepository @Inject constructor(
    private val locksmithHelper: LocksmithHelper,
    private val benefitsService: BenefitsService,
    private val benefitsDAO: BenefitsDAO
) {
    suspend fun getBenefits(): UpaepStandardResponse<List<Benefit>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(error = true, message = locksmith.message)
        val benefits = benefitsService.getBenefits(locksmith.data!!.generalMovilKeychain!!)
        return withContext(Dispatchers.IO) {
            if (!benefits.error) {
                benefitsDAO.deleteBenefits()
                benefitsDAO.addBenefits(benefits.data!!)
            }
            benefits.copy(data = benefitsDAO.getBenefits())
        }
    }

    suspend fun getBenefitsByCategory(benefitCategory: BenefitCategory): UpaepStandardResponse<List<BenefitDetail>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(error = true, message = locksmith.message)
        return benefitsService.getBenefitByCategory(
            keyChain = locksmith.data!!.generalMovilKeychain!!,
            benefitCategory = benefitCategory
        )
    }
}