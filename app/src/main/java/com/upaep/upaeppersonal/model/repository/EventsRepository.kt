package com.upaep.upaeppersonal.model.repository

import com.upaep.upaeppersonal.model.api.features.EventsService
import com.upaep.upaeppersonal.model.base.LocksmithHelper
import com.upaep.upaeppersonal.model.entities.base.PeopleId
import com.upaep.upaeppersonal.model.entities.features.events.EventsResponse
import com.upaep.upaeppersonal.model.entities.upaepservices.UpaepStandardResponse
import javax.inject.Inject

class EventsRepository @Inject constructor(
    private val locksmithHelper: LocksmithHelper,
    private val eventsService: EventsService
) {
    suspend fun getEvents(peopleId: PeopleId) : UpaepStandardResponse<List<EventsResponse>> {
        val locksmith = locksmithHelper()
        if(locksmith.error) return UpaepStandardResponse()
        return eventsService.getEvents(keyChain = locksmith.data!!.eventsKeychain!!, peopleId = peopleId)
    }
}