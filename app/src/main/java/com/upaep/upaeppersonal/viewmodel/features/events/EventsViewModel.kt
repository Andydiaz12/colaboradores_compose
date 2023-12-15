package com.upaep.upaeppersonal.viewmodel.features.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.api.features.EventsService
import com.upaep.upaeppersonal.model.entities.base.PeopleId
import com.upaep.upaeppersonal.model.entities.features.events.EventsResponse
import com.upaep.upaeppersonal.model.repository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val eventsRepository: EventsRepository) :
    ViewModel() {

    val events = mutableListOf<EventsResponse>()
    private val _loadingScreen = MutableLiveData(true)
    val loadingScreen: LiveData<Boolean> = _loadingScreen

    init {
        getEvents()
        _loadingScreen.value = false
    }

    private fun getEvents() {
        viewModelScope.launch {
            val eventsResponse = eventsRepository.getEvents(peopleId = PeopleId(id = 94747))
            if(!eventsResponse.error) {
                events.clear()
                events.addAll(eventsResponse.data!!)
            }
        }
    }
}