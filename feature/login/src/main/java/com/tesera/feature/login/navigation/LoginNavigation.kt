package com.tesera.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tesera.core.ui.NavigationTree
import com.tesera.feature.login.LoginScreen

fun NavGraphBuilder.loginScreen(navController: NavController) {
    composable(route = NavigationTree.Login.name) { LoginScreen(navController) }
}