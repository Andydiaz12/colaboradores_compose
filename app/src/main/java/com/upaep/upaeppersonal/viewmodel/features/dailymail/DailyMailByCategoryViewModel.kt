package com.upaep.upaeppersonal.viewmodel.features.dailymail

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.base.encryption.Base64Helper
import com.upaep.upaeppersonal.model.entities.base.ErrorInfo
import com.upaep.upaeppersonal.model.entities.features.dailymail.DailyMailItem
import com.upaep.upaeppersonal.model.entities.features.dailymail.MailOfDayRequestStructure
import com.upaep.upaeppersonal.model.repository.DailyMailRepository
import com.upaep.upaeppersonal.view.base.navigation.NavigationHelper
import com.upaep.upaeppersonal.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyMailByCategoryViewModel @Inject constructor(
    private val navigationHelper: NavigationHelper,
    private val dailyMailRepository: DailyMailRepository
) :
    ViewModel() {

    val modalContent = mutableStateListOf<DailyMailItem>()

    private val _error = MutableLiveData<ErrorInfo>()
    val error: LiveData<ErrorInfo> = _error

    val items = mutableStateListOf<DailyMailItem>()

    init {
        viewModelScope.launch {
            val response = dailyMailRepository.getDailyByCategory(
                request = MailOfDayRequestStructure(categoryId = DailyMailHelper.getCategoryId())
            )
            _error.value = ErrorInfo(
                error = response.error,
                visible = response.error,
                message = response.message
            )
            if (!response.error) {
                items.addAll(response.data!!)
            }
        }
    }

    fun closeDialog() {
        _error.value = _error.value!!.copy(visible = false)
    }

    fun getItemDesc(item: DailyMailItem, navigation: NavHostController?) {
        DailyMailHelper.setDailyMailItem(dailyMailItem = item)
        navigationHelper(navigation = navigation, route = Routes.DailyMailDescriptionScreen.routes)
    }
}