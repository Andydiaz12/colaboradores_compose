package com.upaep.upaeppersonal.viewmodel.features.assistance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssistanceViewModel @Inject constructor() : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    init {
        initialLoad()
    }

    fun initialLoad() {
        viewModelScope.launch {
            _loading.value = true
            delay(3000)
            _loading.value = false
        }
    }
}