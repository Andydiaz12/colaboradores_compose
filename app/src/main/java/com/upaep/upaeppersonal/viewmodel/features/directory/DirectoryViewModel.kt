package com.upaep.upaeppersonal.viewmodel.features.directory

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.entities.features.directory.DirectoryFilter
import com.upaep.upaeppersonal.model.entities.features.directory.DirectoryPerson
import com.upaep.upaeppersonal.model.repository.DirectoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DirectoryViewModel @Inject constructor(private val directoryRepository: DirectoryRepository) :
    ViewModel() {

    val peopleList = mutableStateListOf<DirectoryPerson>()
    private var getPeopleJob: Job? = null

    val visibleDescription = mutableStateListOf<Boolean>()

    fun getData(data: String) {
        getPeopleJob?.cancel()
        getPeopleJob = viewModelScope.launch {
            val people = directoryRepository.getFilteredData(DirectoryFilter(filters = data))
            if (!people.error) {
                visibleDescription.clear()
                peopleList.clear()
                peopleList.addAll(people.data!!)
                repeat(peopleList.size) {
                    visibleDescription.add(false)
                }
            }
        }
    }

    fun clearData() {
        getPeopleJob?.cancel()
        peopleList.clear()
        visibleDescription.clear()
    }
}