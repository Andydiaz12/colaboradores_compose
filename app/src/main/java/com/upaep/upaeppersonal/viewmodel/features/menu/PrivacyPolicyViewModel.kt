package com.upaep.upaeppersonal.viewmodel.features.menu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.repository.MenuRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrivacyPolicyViewModel @Inject constructor(menuRepository: MenuRepository) : ViewModel() {
    init {
        viewModelScope.launch {
            Log.i("locksmithDebug", menuRepository.getPrivacyPolicy().toString())
        }
    }
}