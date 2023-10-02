package com.upaep.upaeppersonal.viewmodel.features.mailbox

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxFormResponse
import com.upaep.upaeppersonal.model.entities.features.mailbox.MailboxSurveyType
import com.upaep.upaeppersonal.model.entities.features.mailbox.SurveyResponses
import com.upaep.upaeppersonal.model.repository.features.MailboxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class MailboxViewModel @Inject constructor(private val mailboxRepository: MailboxRepository) :
    ViewModel() {

    private val _mailboxOptions = MutableLiveData<List<MailboxSurveyType>>()
    val mailboxOptions: LiveData<List<MailboxSurveyType>> = _mailboxOptions

    private val _selectedOption = MutableLiveData<MailboxSurveyType>()
    val selectedOption: LiveData<MailboxSurveyType> = _selectedOption

    private val _answers = mutableStateListOf<SurveyResponses>()
    val answers: List<SurveyResponses> = _answers

    private val _surveyOptions = mutableStateListOf<MailboxFormResponse>()
    val surveyOptions: List<MailboxFormResponse> = _surveyOptions

    private val _enabledBtn = MutableLiveData<Boolean>()
    val enabledBtn: LiveData<Boolean> = _enabledBtn

    init {
        viewModelScope.launch {
            val values = mailboxRepository.getMailboxOptions()
            _mailboxOptions.value =
                values.data.filter { it.isSatisfactionSurvey != 1 }.sortedBy { it.topicId }
            if (_mailboxOptions.value!!.isNotEmpty()) {
                getMailBoxSurvey(surveyType = _mailboxOptions.value!!.first())
            }
        }
    }

    fun getMailBoxSurvey(surveyType: MailboxSurveyType) {
        viewModelScope.launch {
            _selectedOption.value = surveyType
            val response =
                mailboxRepository.getMailboxSurvey(surveyId = surveyType.topicId).data.sortedBy { it.order }
            _answers.clear()
            _surveyOptions.clear()
            response.forEach { element ->
                _answers.add(
                    SurveyResponses(
                        itemId = element.itemId,
                        componentType = element.componentType,
                        required = element.required
                    )
                )
            }
            submitAnswers()
            _surveyOptions.addAll(response)
        }
    }

    fun changeSelectedOption(option: MailboxSurveyType) {
        _selectedOption.value = option
    }

    fun inputChange(element: MailboxFormResponse, value: String) {
        val element = _answers.find { it.itemId == element.itemId }
        val index = _answers.indexOf(element)
        _answers[index] = _answers[index].let {
            it.copy(responseValue = value)
        }
//        _answers.find { it.itemId == element.itemId }?.responseValue = value
        _enabledBtn.value = enabledBtn()
    }

    fun submitAnswers() {
        _answers.forEachIndexed { index, ans ->
            Log.i("submitAnswers_${index}", ans.toString())
        }
    }

    private fun enabledBtn(): Boolean =
        !_answers.any { element -> (element.required == true && element.responseValue.isNullOrBlank()) || (element.componentType == 4 && !element.responseValue.toBoolean()) }

}