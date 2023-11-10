package com.upaep.upaeppersonal.viewmodel.features.upress

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.model.repository.UpressRepository
import com.upaep.upaeppersonal.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpressViewModel @Inject constructor(upressRepository: UpressRepository) : ViewModel() {
    private val _upressElements = mutableStateListOf<UpressItem>()
    val upressElements: List<UpressItem> = _upressElements

    init {
        viewModelScope.launch {
            _upressElements.addAll(upressRepository.getUpressContent())
        }
    }

    fun navigateDescScreen(item: UpressItem, navigation: NavHostController?) {
        UpressData.setData(item = item)
        navigation?.navigate(Routes.UpressDescScreen.routes) {
            launchSingleTop = true
        }
    }
}