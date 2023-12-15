package com.upaep.upaeppersonal.model.repository

import android.util.Log
import com.upaep.upaeppersonal.model.api.LocksmithService
import com.upaep.upaeppersonal.model.database.locksmithdao.LocksmithDAO
import com.upaep.upaeppersonal.model.entities.features.locksmith.Locksmith
import javax.inject.Inject

class LocksmithRepository @Inject constructor(
    private val locksmithService: LocksmithService,
    private val locksmithDAO: LocksmithDAO
) {
    suspend fun getLocksmithFromService(): Boolean {
        val response = locksmithService.getLocksmith()
        if (!response.error) {
            locksmithDAO.deleteLocksmith()
            locksmithDAO.addLocksmith(locksmith = response.data!!)
        }
        return !response.error
    }
}