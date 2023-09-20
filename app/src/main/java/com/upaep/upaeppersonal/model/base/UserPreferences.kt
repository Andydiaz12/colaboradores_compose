package com.upaep.upaeppersonal.model.base

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.upaep.upaeppersonal.model.entities.theme.ActiveTheme
import com.upaep.upaeppersonal.view.base.theme.ThemeSchema
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(context: Context) {
    private val pref = context.dataStore
    private val gson = Gson()

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore("USER_PREFERENCES")
        private var ACTIVE_THEME = stringPreferencesKey("ACTIVE_THEME")
        private var SELECTED_THEME = booleanPreferencesKey("SELECTED_THEME")
    }

    suspend fun setTheme(newTheme: ThemeSchema) {
        pref.edit {
            it[ACTIVE_THEME] = gson.toJson(newTheme)
            it[SELECTED_THEME] = true
        }
    }

    val activeTheme: Flow<ActiveTheme?> = pref.data.map {
        gson.fromJson(it[ACTIVE_THEME], ActiveTheme::class.java)
    }

    val selectedTheme: Flow<Boolean> = pref.data.map {
        it[SELECTED_THEME] ?: false
    }
}