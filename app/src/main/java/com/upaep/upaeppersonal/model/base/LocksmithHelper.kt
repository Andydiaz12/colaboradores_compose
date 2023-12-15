package com.upaep.upaeppersonal.model.base

import com.upaep.upaeppersonal.model.api.LocksmithService
import com.upaep.upaeppersonal.model.database.locksmithdao.LocksmithDAO
import com.upaep.upaeppersonal.model.entities.features.locksmith.Locksmith
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocksmithHelper @Inject constructor(
    private val locksmithService: LocksmithService,
    private val locksmithDAO: LocksmithDAO
) {
    suspend operator fun invoke(): UpaepStandardResponse<Locksmith> {
        return withContext(Dispatchers.IO) {
            val locksmith = locksmithDAO.getLocksmith()
            if (locksmith.isEmpty()) {
                locksmithService.getLocksmith()
            } else {
                UpaepStandardResponse(data = locksmith.first(), error = false)
            }
        }
    }
}