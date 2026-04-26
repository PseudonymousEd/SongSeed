package com.songseed.songseedclaude.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ImprovPrompt : Screen("improv_prompt")
    object RhymeDrill : Screen("rhyme_drill")
    object SlantRhymeDrill : Screen("slant_rhyme_drill")
    object LooseRhymes : Screen("loose_rhymes")
    object HardEndingShiftDescription : Screen("hard_ending_shift_description")
    object HardEndingShiftDrill : Screen("hard_ending_shift_drill")
    object MultiSyllableDescription : Screen("multi_syllable_description")
    object MultiSyllableDrill : Screen("multi_syllable_drill")
    object Settings : Screen("settings")
}
