package com.songseed.songseedclaude.viewmodel

import androidx.lifecycle.ViewModel
import com.songseed.songseedclaude.data.MultiSyllableData
import com.songseed.songseedclaude.data.MultiSyllablePair
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MultiSyllableViewModel : ViewModel() {

    private val _currentPair = MutableStateFlow<MultiSyllablePair?>(null)

    private val _seedWord = MutableStateFlow("")
    val seedWord: StateFlow<String> = _seedWord.asStateFlow()

    private val _exampleWord = MutableStateFlow("")
    val exampleWord: StateFlow<String> = _exampleWord.asStateFlow()

    private val _isExampleVisible = MutableStateFlow(false)
    val isExampleVisible: StateFlow<Boolean> = _isExampleVisible.asStateFlow()

    fun nextWord() {
        val current = _currentPair.value
        val candidates = MultiSyllableData.allPairs.filter { it != current }.ifEmpty { MultiSyllableData.allPairs }
        val pair = candidates.random()
        _currentPair.value = pair
        if ((0..1).random() == 0) {
            _seedWord.value = pair.wordA
            _exampleWord.value = pair.wordB
        } else {
            _seedWord.value = pair.wordB
            _exampleWord.value = pair.wordA
        }
        _isExampleVisible.value = false
    }

    fun showExample() {
        _isExampleVisible.value = true
    }
}
