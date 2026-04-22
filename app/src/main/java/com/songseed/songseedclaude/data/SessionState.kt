package com.songseed.songseedclaude.data

data class SessionState(
    val recentPrompts: MutableList<String> = mutableListOf(),
    val recentWords: MutableList<String> = mutableListOf()
) {
    fun addPrompt(prompt: String, maxSize: Int = 10) {
        recentPrompts.add(prompt)
        if (recentPrompts.size > maxSize) recentPrompts.removeAt(0)
    }

    fun addWord(word: String, maxSize: Int = 10) {
        recentWords.add(word)
        if (recentWords.size > maxSize) recentWords.removeAt(0)
    }
}
