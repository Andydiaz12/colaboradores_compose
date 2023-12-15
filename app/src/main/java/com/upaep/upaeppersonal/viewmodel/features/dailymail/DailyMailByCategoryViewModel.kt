package com.upaep.upaeppersonal.viewmodel.features.dailymail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.entities.base.ErrorInfo
import com.upaep.upaeppersonal.model.entities.features.dailymail.MailOfDayRequestStructure
import com.upaep.upaeppersonal.model.repository.DailyMailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyMailByCategoryViewModel @Inject constructor(dailyMailRepository: DailyMailRepository) : ViewModel() {

    private val _error = MutableLiveData<ErrorInfo>()
    val error: LiveData<ErrorInfo> = _error

    init {
        viewModelScope.launch {
            val response = dailyMailRepository.getDailyByCategory(request = MailOfDayRequestStructure(categoryId = DailyMailHelper.getCategoryId()))
            _error.value = ErrorInfo(error = response.error, visible = response.error, message = response.message)
            if(!response.error) {

            }
        }
    }

    fun closeDialog() {
        _error.value = _error.value!!.copy(visible = false)
    }
}