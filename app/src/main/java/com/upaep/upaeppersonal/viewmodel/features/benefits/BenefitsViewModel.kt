package com.upaep.upaeppersonal.viewmodel.features.benefits

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.upaep.upaeppersonal.model.entities.base.ErrorInfo
import com.upaep.upaeppersonal.model.entities.features.benefits.Benefit
import com.upaep.upaeppersonal.model.repository.BenefitsRepository
import com.upaep.upaeppersonal.view.base.navigation.Routes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BenefitsViewModel @Inject constructor(private val benefitsRepository: BenefitsRepository) :
    ViewModel() {

    val benefits = mutableStateListOf<Benefit>()
    private val _loadingScreen = MutableLiveData(true)
    val loadingScreen: LiveData<Boolean> = _loadingScreen

    private val _error = MutableLiveData<ErrorInfo>()
    val error: LiveData<ErrorInfo> = _error

    init {
        viewModelScope.launch {
            initialData()
            _loadingScreen.value = false
        }
    }

    fun selectCategory(categoryId: Int, navigation: NavHostController?) {
        BenefitsHelper(categoryId = categoryId)
        navigation?.navigate(Routes.BenefitsByCategoryScreen.routes) {
            launchSingleTop = true
        }
    }

    fun closeDialog() {
        _error.value = _error.value!!.copy(visible = false)
    }

    private suspend fun initialData() {
        val response = benefitsRepository.getBenefits()
        _error.value = ErrorInfo(error = response.error, message = response.message, visible = response.error)
        benefits.clear()
        benefits.addAll(response.data ?: emptyList())
    }
}