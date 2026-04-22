package com.songseed.songseedclaude.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.songseed.songseedclaude.data.ImprovData
import com.songseed.songseedclaude.data.SessionState
import com.songseed.songseedclaude.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ImprovViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository = SettingsRepository(application)
    private val sessionState = SessionState()

    private val _currentPrompt = MutableStateFlow("")
    val currentPrompt: StateFlow<String> = _currentPrompt.asStateFlow()

    fun generatePrompt(mode: String) {
        viewModelScope.launch {
            val avoidRepeats = settingsRepository.avoidRecentRepeats.first()
            val recentList = if (avoidRepeats) sessionState.recentPrompts.toList() else emptyList()
            val prompt = when (mode) {
                "difficult" -> ImprovData.generateDifficultPrompt(recentList)
                else -> ImprovData.generateNormalPrompt(recentList)
            }
            sessionState.addPrompt(prompt)
            _currentPrompt.value = prompt
        }
    }
}
