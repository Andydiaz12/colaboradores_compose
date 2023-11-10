package com.upaep.upaeppersonal.model.repository

import android.util.Log
import com.upaep.upaeppersonal.model.api.LocksmithService
import javax.inject.Inject

class LocksmithRepository @Inject constructor(private val locksmithService: LocksmithService) {
    suspend fun getLocksmith() {
        Log.i("getLocksmith", "repository")
        locksmithService.getLocksmith()
    }
}