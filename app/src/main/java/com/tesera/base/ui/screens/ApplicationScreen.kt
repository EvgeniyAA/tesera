package com.tesera.base.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tesera.designsystem.theme.AppTheme
import com.tesera.core.ui.NavigationTree
import com.tesera.feature.dashboard.DashboardScreen
import com.tesera.feature.login.LoginScreen
import com.tesera.feature.splash.SplashScreen

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationTree.Splash.name) {
        composable(route = NavigationTree.Splash.name) {
            ScreenContainer { SplashScreen(navController) }
        }
        composable(route = NavigationTree.Login.name) {
            ScreenContainer { LoginScreen(navController) }
        }
        composable(route = NavigationTree.Dashboard.name) {
            ScreenContainer { DashboardScreen() }
        }
    }
}

@Composable
private fun ScreenContainer(screen: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppTheme.colors.primaryBackground
    ) { screen() }
}