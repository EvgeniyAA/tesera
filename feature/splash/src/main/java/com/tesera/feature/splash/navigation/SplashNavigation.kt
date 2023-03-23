package com.tesera.feature.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tesera.core.ui.NavigationTree
import com.tesera.feature.splash.SplashScreen

fun NavGraphBuilder.splashScreen(navController: NavController) {
    composable(route = NavigationTree.Splash.name) { SplashScreen(navController = navController) }
}