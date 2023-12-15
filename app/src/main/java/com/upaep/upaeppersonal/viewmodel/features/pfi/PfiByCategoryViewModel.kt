package com.upaep.upaeppersonal.viewmodel.features.pfi

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiByCategory
import com.upaep.upaeppersonal.model.entities.features.pfi.PfiCategoryResponse
import com.upaep.upaeppersonal.model.repository.PfiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PfiByCategoryViewModel @Inject constructor(private val pfiRepository: PfiRepository) :
    ViewModel() {
    val categories = mutableStateListOf<PfiCategoryResponse>()

    private val _loadingScreen = MutableLiveData(true)
    val loadingScreen: LiveData<Boolean> = _loadingScreen

    private val _screenName = MutableLiveData("")
    val screenName: LiveData<String> = _screenName

    init {
        _screenName.value = PfiHelper.getCategoryName()
        viewModelScope.launch {
            val response =
                pfiRepository.getPfiByCategory(categoryId = PfiByCategory(categoryId = PfiHelper.getCategory()))
            if (!response.error) {
                categories.addAll(response.data!!)
            }
            _loadingScreen.value = false
        }
    }
}