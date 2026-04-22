package com.songseed.songseedclaude.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.songseed.songseedclaude.data.SessionState
import com.songseed.songseedclaude.data.SettingsRepository
import com.songseed.songseedclaude.data.WordData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RhymeViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsRepository = SettingsRepository(application)
    private val sessionState = SessionState()

    private val _currentWord = MutableStateFlow("")
    val currentWord: StateFlow<String> = _currentWord.asStateFlow()

    private val _isEasyMode = MutableStateFlow(true)
    val isEasyMode: StateFlow<Boolean> = _isEasyMode.asStateFlow()

    fun toggleDifficulty() {
        _isEasyMode.value = !_isEasyMode.value
    }

    fun nextWord() {
        viewModelScope.launch {
            val avoidRepeats = settingsRepository.avoidRecentRepeats.first()
            val pool = if (_isEasyMode.value) WordData.getEasyWords() else WordData.getNormalWords()
            val candidates = if (avoidRepeats) {
                pool.filter { !sessionState.recentWords.contains(it.text) }.ifEmpty { pool }
            } else {
                pool
            }
            val word = candidates.random()
            sessionState.addWord(word.text)
            _currentWord.value = word.text
        }
    }
}
