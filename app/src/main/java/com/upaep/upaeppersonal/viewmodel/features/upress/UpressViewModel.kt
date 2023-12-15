package com.upaep.upaeppersonal.viewmodel.features.upress

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.entities.features.upress.UpressCategoryRequest
import com.upaep.upaeppersonal.model.entities.features.upress.UpressItem
import com.upaep.upaeppersonal.model.entities.features.upress.UpressOptions
import com.upaep.upaeppersonal.model.repository.UpressRepository
import com.upaep.upaeppersonal.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpressViewModel @Inject constructor(private val upressRepository: UpressRepository) : ViewModel() {
    private val _upressElements = mutableStateListOf<UpressItem>()
    val upressElements: List<UpressItem> = _upressElements

    private val _selectedOption = MutableLiveData<UpressOptions>()
    val selectedOption: LiveData<UpressOptions> = _selectedOption

    val upressOptionList = mutableStateListOf<UpressOptions>()

    init {
        viewModelScope.launch {
            val upress = upressRepository.getUpressCategories()
            if (!upress.error) {
                upressOptionList.addAll(upress.data!!)
                if (upressOptionList.isNotEmpty()) {
                    val firstElement = upressOptionList.first()
                    _selectedOption.value = firstElement
                    getContent(categoryId = firstElement.id!!)
                }
            }
        }
    }

    private fun getContent(categoryId: Int) {
        viewModelScope.launch {
            val elements =
                upressRepository.getUpressContent(UpressCategoryRequest(categoryId = categoryId))
            if(!elements.error) {
                _upressElements.clear()
                _upressElements.addAll(elements.data!!)
            }
        }
    }

    fun navigateDescScreen(item: UpressItem, navigation: NavHostController?) {
        UpressData.setData(item = item)
        navigation?.navigate(Routes.UpressDescScreen.routes) {
            launchSingleTop = true
        }
    }

    fun changeSelectedOption(element: UpressOptions) {
        _selectedOption.value = element
        getContent(element.id!!)
    }
}