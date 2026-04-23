package com.songseed.songseedcodex.data

import com.songseed.songseedcodex.data.local.SeedData
import com.songseed.songseedcodex.domain.RhymeDifficulty
import com.songseed.songseedcodex.domain.Word
import kotlin.random.Random

class RhymeRepository(
    private val random: Random = Random.Default
) {
    fun nextWord(
        difficulty: RhymeDifficulty,
        recentWords: List<String>,
        avoidRecentRepeats: Boolean
    ): Word {
        val candidates = SeedData.rhymeWords.filter {
            when (difficulty) {
                RhymeDifficulty.Easy -> it.isEasy()
                RhymeDifficulty.Normal -> !it.isEasy()
            }
        }

        val primaryPool = if (avoidRecentRepeats) {
            candidates.filterNot { it.text in recentWords }.ifEmpty { candidates }
        } else {
            candidates
        }

        return primaryPool.random(random)
    }

    private fun Word.isEasy(): Boolean = syllableCount == 1 || text.length <= 5
}
