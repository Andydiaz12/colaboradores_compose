package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.features.PfiService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiByCategory
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiCategories
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiCategoryResponse
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PfiRepository @Inject constructor(
    private val pfiService: PfiService,
    private val locksmithHelper: LocksmithHelper,
) {
    suspend fun getPfiCategories(): UpaepStandardResponse<List<PfiCategories>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(error = true, message = locksmith.message)
        val pfiCategories =
            pfiService.getPfiCategories(keyChain = locksmith.data!!.apiCollaboratorsPfiKeychain!!)
        return withContext(Dispatchers.IO) {
            if (!pfiCategories.error) {

            }
            pfiCategories.copy()
        }
    }

    suspend fun getPfiByCategory(categoryId: PfiByCategory): UpaepStandardResponse<List<PfiCategoryResponse>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(error = true, message = locksmith.message)
        val categories = pfiService.getPfiByCategory(
            keyChain = locksmith.data!!.apiCollaboratorsPfiKeychain!!,
            categoryId = categoryId
        )
        return withContext(Dispatchers.IO) {
            if (!categories.error) {

            }
            categories
        }
    }
}