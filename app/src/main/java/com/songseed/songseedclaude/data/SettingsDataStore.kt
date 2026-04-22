package com.songseed.songseedclaude.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

private object SettingsKeys {
    val AVOID_RECENT_REPEATS = booleanPreferencesKey("avoid_recent_repeats")
}

class SettingsRepository(private val context: Context) {

    val avoidRecentRepeats: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[SettingsKeys.AVOID_RECENT_REPEATS] ?: true }

    suspend fun setAvoidRecentRepeats(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.AVOID_RECENT_REPEATS] = value
        }
    }
}
