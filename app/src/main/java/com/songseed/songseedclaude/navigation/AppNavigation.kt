package com.songseed.songseedclaude.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.songseed.songseedclaude.ui.screens.HomeScreen
import com.songseed.songseedclaude.ui.screens.ImprovModeSelectionScreen
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
                onImprovClick = { navController.navigate(Screen.ImprovModeSelection.route) },
                onRhymeDrillClick = { navController.navigate(Screen.RhymeDrill.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.ImprovModeSelection.route) {
            ImprovModeSelectionScreen(
                onNormalModeClick = {
                    navController.navigate(Screen.ImprovPrompt.createRoute("normal"))
                },
                onDifficultModeClick = {
                    navController.navigate(Screen.ImprovPrompt.createRoute("difficult"))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.ImprovPrompt.route,
            arguments = listOf(navArgument("mode") { type = NavType.StringType })
        ) { backStackEntry ->
            val mode = backStackEntry.arguments?.getString("mode") ?: "normal"
            ImprovPromptScreen(
                mode = mode,
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
