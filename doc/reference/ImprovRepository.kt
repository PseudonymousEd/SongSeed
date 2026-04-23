package com.songseed.songseedcodex.data

import com.songseed.songseedcodex.data.local.SeedData
import com.songseed.songseedcodex.domain.ImprovCategory
import com.songseed.songseedcodex.domain.ImprovMode
import com.songseed.songseedcodex.domain.ImprovPrompt
import com.songseed.songseedcodex.domain.PromptPiece
import kotlin.random.Random

class ImprovRepository(
    private val random: Random = Random.Default
) {
    fun generatePrompt(
        mode: ImprovMode,
        enabledCategories: Set<ImprovCategory>,
        recentPrompts: List<String>,
        avoidRecentRepeats: Boolean
    ): ImprovPrompt {
        val categories = enabledCategories.ifEmpty { ImprovCategory.values().toSet() }.toList()
        val prompt = when (mode) {
            ImprovMode.Normal -> buildNormalPrompt(categories)
            ImprovMode.Difficult -> buildDifficultPrompt(categories)
        }

        if (!avoidRecentRepeats || recentPrompts.isEmpty() || prompt.text !in recentPrompts) {
            return prompt
        }

        repeat(10) {
            val retry = when (mode) {
                ImprovMode.Normal -> buildNormalPrompt(categories)
                ImprovMode.Difficult -> buildDifficultPrompt(categories)
            }
            if (retry.text !in recentPrompts) {
                return retry
            }
        }

        return prompt
    }

    private fun buildNormalPrompt(categories: List<ImprovCategory>): ImprovPrompt {
        val category = categories.random(random)
        val item = SeedData.improvItems.getValue(category).random(random)
        val text = when (category) {
            ImprovCategory.Themes -> "Sing about $item"
            ImprovCategory.Images -> "Sing about $item"
            ImprovCategory.Characters -> "Sing as $item"
            ImprovCategory.Situations -> "Sing about a moment $item"
            ImprovCategory.Emotions -> "Express $item"
            ImprovCategory.Places -> "Sing about $item"
            ImprovCategory.Goals -> "Sing about wanting to $item"
            ImprovCategory.Obstacles -> "Sing about a problem: $item"
            ImprovCategory.Twists -> "Sing about something where $item"
            ImprovCategory.Elements -> "Include $item"
        }
        return ImprovPrompt(text, listOf(PromptPiece(category, item)))
    }

    private fun buildDifficultPrompt(categories: List<ImprovCategory>): ImprovPrompt {
        val targetCount = random.nextInt(2, 4)
        val selected = buildList {
            val shuffled = categories.shuffled(random)
            addAll(shuffled.take(targetCount))
            while (size < targetCount) {
                add(categories.random(random))
            }
        }
        val pieces = selected.map { category ->
            PromptPiece(category, SeedData.improvItems.getValue(category).random(random))
        }

        val byCategory = pieces.associateBy { it.category }
        val text = when {
            byCategory.containsKey(ImprovCategory.Goals) && byCategory.containsKey(ImprovCategory.Obstacles) -> {
                "You want to ${byCategory.getValue(ImprovCategory.Goals).value}, but ${byCategory.getValue(ImprovCategory.Obstacles).value}"
            }
            byCategory.containsKey(ImprovCategory.Characters) && byCategory.containsKey(ImprovCategory.Situations) -> {
                "You are ${byCategory.getValue(ImprovCategory.Characters).value} ${byCategory.getValue(ImprovCategory.Situations).value}"
            }
            byCategory.containsKey(ImprovCategory.Emotions) && byCategory.containsKey(ImprovCategory.Places) -> {
                "Express ${byCategory.getValue(ImprovCategory.Emotions).value} in ${byCategory.getValue(ImprovCategory.Places).value}"
            }
            byCategory.containsKey(ImprovCategory.Themes) && byCategory.containsKey(ImprovCategory.Images) -> {
                "Sing about ${byCategory.getValue(ImprovCategory.Themes).value} using ${byCategory.getValue(ImprovCategory.Images).value} as a metaphor"
            }
            else -> {
                val fragments = pieces.joinToString(", ") { piece ->
                    when (piece.category) {
                        ImprovCategory.Characters -> "as ${piece.value}"
                        ImprovCategory.Emotions -> "with ${piece.value}"
                        ImprovCategory.Places -> "in ${piece.value}"
                        else -> piece.value
                    }
                }
                "Create a song that includes $fragments"
            }
        }

        return ImprovPrompt(text, pieces)
    }
}
