package com.upaep.upaeppersonal.viewmodel.base

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.model.repository.LocksmithRepository
import com.upaep.upaeppersonal.view.base.theme.ThemeSchema
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    locksmithRepository: LocksmithRepository
) : ViewModel() {

    private var userPreferences = UserPreferences(application)

    private val _activeTheme = userPreferences.activeTheme
    val activeTheme: Flow<ActiveTheme?> = _activeTheme

    init {
        viewModelScope.launch {
            Log.i("getLocksmith", "viewmodel")
            locksmithRepository.getLocksmith()
        }
    }

    fun setTheme(activeTheme: ThemeSchema) {
        viewModelScope.launch {
            userPreferences.setTheme(activeTheme)
        }
    }
}