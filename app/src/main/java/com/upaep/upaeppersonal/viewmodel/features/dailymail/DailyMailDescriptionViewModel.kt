package com.upaep.upaeppersonal.viewmodel.features.dailymail

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailItem
import com.upaep.upaeppersonal.model.repository.DailyMailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyMailDescriptionViewModel @Inject constructor(dailyMailRepository: DailyMailRepository) :
    ViewModel() {

    private val _loadingScreen = MutableLiveData(true)
    val loadingScreen: LiveData<Boolean> = _loadingScreen

    val screenContent = mutableStateListOf<DailyMailItem>()

    init {
        viewModelScope.launch {
            val response =
                dailyMailRepository.getItemDescription(item = DailyMailHelper.getDailyMailItem()!!)
            if (!response.error) {
                response.data!!.map {
                    it.content = Base64Helper.decodeBase64(it.content ?: "")
                }
                screenContent.addAll(response.data)
            }
            _loadingScreen.value = false
        }
    }
}