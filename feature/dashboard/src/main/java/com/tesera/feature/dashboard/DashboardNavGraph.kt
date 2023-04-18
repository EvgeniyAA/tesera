package com.tesera.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tesera.core.ui.NavigationTree
import com.tesera.feature.gamedetails.GameDetailsScreen
import com.tesera.feature.games.GamesScreen
import com.tesera.feature.home.HomeScreen
import com.tesera.feature.profile.ProfileScreen
import com.tesera.feature.search.SearchScreen

@Composable
fun DashboardNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationTree.Home.name
    ) {
        composable(route = NavigationTree.Home.name) {
            HomeScreen(navController)
        }
        composable(route = NavigationTree.Search.name) {
            SearchScreen(navController)
        }
        composable(route = NavigationTree.Profile.name) {
            ProfileScreen(navController)
        }
        composable(route = NavigationTree.Games.name) {
            GamesScreen(navController)
        }
        composable(route = "${NavigationTree.GamesDetails.name}/{alias}") {
            GameDetailsScreen(navController, it.arguments?.getString("alias").orEmpty())
        }
    }
}