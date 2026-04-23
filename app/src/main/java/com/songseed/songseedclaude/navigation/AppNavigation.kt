package com.songseed.songseedclaude.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.songseed.songseedclaude.ui.screens.HomeScreen
import com.songseed.songseedclaude.ui.screens.ImprovPromptScreen
import com.songseed.songseedclaude.ui.screens.RhymeDrillScreen
import com.songseed.songseedclaude.ui.screens.SettingsScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onImprovClick = { navController.navigate(Screen.ImprovPrompt.route) },
                onRhymeDrillClick = { navController.navigate(Screen.RhymeDrill.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.ImprovPrompt.route) {
            ImprovPromptScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.RhymeDrill.route) {
            RhymeDrillScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
