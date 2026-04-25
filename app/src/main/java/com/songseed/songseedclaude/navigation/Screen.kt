package com.songseed.songseedclaude.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ImprovPrompt : Screen("improv_prompt")
    object RhymeDrill : Screen("rhyme_drill")
    object SlantRhymeDrill : Screen("slant_rhyme_drill")
    object Settings : Screen("settings")
}
