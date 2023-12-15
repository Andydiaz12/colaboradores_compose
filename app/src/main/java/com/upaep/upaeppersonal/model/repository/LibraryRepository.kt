package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.features.LibraryService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.entities.base.PeopleId
import com.upaep.upaeppersonal.model.entities.features.library.BorrowedBook
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import javax.inject.Inject

class LibraryRepository @Inject constructor(
    private val locksmithHelper: LocksmithHelper,
    private val libraryService: LibraryService
) {

    suspend fun getBorrowedBooks(peopleId: PeopleId): UpaepStandardResponse<List<BorrowedBook>> {
        val locksmith = locksmithHelper()
        if (locksmith.error) return UpaepStandardResponse(message = locksmith.message)
        return libraryService.getBorrowedBooks(
            keyChain = locksmith.data!!.generalLibraryKeychain!!,
            peopleId = peopleId
        )
    }

}