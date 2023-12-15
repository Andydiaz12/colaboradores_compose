package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.features.UpressService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.entities.features.upress.UpressCategoryRequest
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.model.entities.features.upress.UpressOptions
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import javax.inject.Inject

class UpressRepository @Inject constructor(
    private val locksmithHelper: LocksmithHelper,
    private val upressService: UpressService
) {
    suspend fun getUpressContent(categoryId: UpressCategoryRequest): UpaepStandardResponse<List<UpressItem>> {
        val locksmith = locksmithHelper()
        if(locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        return upressService.getUpressContent(keyChain = locksmith.data!!.generalMovilKeychain!!, categoryId = categoryId)
    }

    suspend fun getUpressCategories(): UpaepStandardResponse<List<UpressOptions>> {
        val locksmith = locksmithHelper()
        if(locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        return upressService.getUpressCategories(keyChain = locksmith.data!!.generalMovilKeychain!!)
    }
}