package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.features.DirectoryService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.entities.features.directory.DirectoryFilter
import com.upaep.upaeppersonal.model.entities.features.directory.DirectoryPerson
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import javax.inject.Inject

class DirectoryRepository @Inject constructor(
    private val locksmithHelper: LocksmithHelper,
    private val directoryService: DirectoryService
) {
    suspend fun getFilteredData(filter: DirectoryFilter): UpaepStandardResponse<List<DirectoryPerson>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) UpaepStandardResponse<Any>()
        return directoryService.getFilteredData(
            keyChain = locksmith.data!!.generalMovilKeychain!!,
            filter = filter
        )
    }
}