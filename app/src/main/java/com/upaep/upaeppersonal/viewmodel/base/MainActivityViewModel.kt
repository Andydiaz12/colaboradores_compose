package com.upaep.upaeppersonal.viewmodel.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upaep.upaeppersonal.model.base.UserPreferences
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.theme.ThemeSchema
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(application: Application) : ViewModel() {

    private var userPreferences = UserPreferences(application)

    private val _activeTheme = userPreferences.activeTheme
    val activeTheme: Flow<ActiveTheme?> = _activeTheme

    fun setTheme(activeTheme: ThemeSchema) {
        viewModelScope.launch {
            userPreferences.setTheme(activeTheme)
        }
    }
}