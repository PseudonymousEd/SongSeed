package com.songseed.songseedclaude.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ImprovModeSelection : Screen("improv_mode_selection")
    object ImprovPrompt : Screen("improv_prompt/{mode}") {
        fun createRoute(mode: String) = "improv_prompt/$mode"
    }
    object RhymeDrill : Screen("rhyme_drill")
    object Settings : Screen("settings")
}
