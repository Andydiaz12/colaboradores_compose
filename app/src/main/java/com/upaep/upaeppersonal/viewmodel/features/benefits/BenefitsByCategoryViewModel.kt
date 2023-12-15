package com.upaep.upaeppersonal.viewmodel.features.benefits

import android.util.Log
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.entities.base.ErrorInfo
import com.upaep.upaeppersonal.model.entities.features.benefits.BenefitCategory
import com.upaep.upaeppersonal.model.entities.features.benefits.BenefitDetail
import com.upaep.upaeppersonal.model.repository.BenefitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BenefitsByCategoryViewModel @Inject constructor(
    benefitsRepository: BenefitsRepository
) : ViewModel() {

    private val _error = MutableLiveData<ErrorInfo?>(null)
    val error: LiveData<ErrorInfo?> = _error

    private val _loadingScreen = MutableLiveData(true)
    val loadingScreen: LiveData<Boolean> = _loadingScreen

    val benefit = mutableStateListOf<BenefitDetail>()

    init {
        viewModelScope.launch {
            val response = benefitsRepository.getBenefitsByCategory(
                benefitCategory = BenefitCategory(categoryId = BenefitsHelper.getCategory())
            )
            Log.i("benefitsDebug_vm", response.toString())
            _error.value = ErrorInfo(visible = response.error, message = response.message)
            _loadingScreen.value = false
            if (!response.error) {
                benefit.addAll(response.data!!)
            }
        }
    }

    fun hideDialog() {
        _error.value = _error.value!!.copy(visible = false)
    }
}