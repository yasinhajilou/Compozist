package com.example.lazylistsample

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lazylistsample.ui.explorer.ExplorerScreen
import com.example.lazylistsample.ui.welcome.WelcomeScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = "welcome"
    ) {
        composable(route = "welcome") {
            WelcomeScreen {
                navController.navigate("explorer") {
                    popUpTo(navController.findDestination("welcome")?.id ?: return@navigate) {
                        inclusive
                        saveState = false
                    }
                    restoreState = false
                }
            }
        }
        composable("explorer") {
            ExplorerScreen()
        }
    }
}