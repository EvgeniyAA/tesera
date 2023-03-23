package com.tesera.base.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tesera.core.ui.NavigationTree
import com.tesera.feature.login.navigation.loginScreen
import com.tesera.feature.splash.navigation.splashScreen

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationTree.Splash.name) {
        splashScreen(navController)
        loginScreen(navController)
        dashboardScreen(navController)
    }
}