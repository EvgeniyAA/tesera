package com.tesera.base.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tesera.core.designsystem.theme.AppTheme
import com.tesera.core.ui.NavigationTree

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            name = "Home",
            route = NavigationTree.Home.name,
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = "Search",
            route = NavigationTree.Search.name,
            icon = Icons.Rounded.Search,
        ),
        BottomNavItem(
            name = "Profile",
            route = NavigationTree.Profile.name,
            icon = Icons.Rounded.Person,
        )
    )

    val backStackEntry = navController.currentBackStackEntryAsState()

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = AppTheme.colors.primaryBackground,
            ) {
                bottomNavItems.forEach { item ->
                    val selected = item.route == backStackEntry.value?.destination?.route

                    NavigationBarItem(
                        selected = selected,
                        onClick = { navController.navigate(item.route) },
                        label = {
                            Text(
                                text = item.name,
                                fontWeight = FontWeight.SemiBold,
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = "${item.name} Icon",
                            )
                        }
                    )
                }
            }
        },
        content = {
            it.calculateBottomPadding()
        }
    )
}

fun NavGraphBuilder.dashboardScreen(navController: NavController) {
    composable(route = NavigationTree.Dashboard.name) { DashboardScreen(navController = navController) }
}