package com.songseed.songseedclaude.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.songseed.songseedclaude.ui.screens.HardEndingShiftDescriptionScreen
import com.songseed.songseedclaude.ui.screens.HardEndingShiftDrillScreen
import com.songseed.songseedclaude.ui.screens.HomeScreen
import com.songseed.songseedclaude.ui.screens.ImprovPromptScreen
import com.songseed.songseedclaude.ui.screens.LooseRhymesSubcategoryScreen
import com.songseed.songseedclaude.ui.screens.RhymeDrillScreen
import com.songseed.songseedclaude.ui.screens.SettingsScreen
import com.songseed.songseedclaude.ui.screens.SlantRhymeDrillScreen

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
                onLooseRhymesClick = { navController.navigate(Screen.LooseRhymes.route) },
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
        composable(Screen.SlantRhymeDrill.route) {
            SlantRhymeDrillScreen(
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.LooseRhymes.route) {
            LooseRhymesSubcategoryScreen(
                onBack = { navController.popBackStack() },
                onDefaultClick = { navController.navigate(Screen.SlantRhymeDrill.route) },
                onHardEndingShiftClick = { navController.navigate(Screen.HardEndingShiftDescription.route) }
            )
        }
        composable(Screen.HardEndingShiftDescription.route) {
            HardEndingShiftDescriptionScreen(
                onBack = { navController.popBackStack() },
                onStartDrill = { navController.navigate(Screen.HardEndingShiftDrill.route) }
            )
        }
        composable(Screen.HardEndingShiftDrill.route) {
            HardEndingShiftDrillScreen(
                onBack = { navController.popBackStack() },
                onHome = { navController.popBackStack(Screen.Home.route, inclusive = false) }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
