package com.songseed.songseedclaude.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.songseed.songseedclaude.data.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SettingsRepository(application)

    val avoidRecentRepeats: StateFlow<Boolean> = repository.avoidRecentRepeats
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setAvoidRecentRepeats(value: Boolean) {
        viewModelScope.launch {
            repository.setAvoidRecentRepeats(value)
        }
    }
}
