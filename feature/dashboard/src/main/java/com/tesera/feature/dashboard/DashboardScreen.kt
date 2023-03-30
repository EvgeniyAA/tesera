package com.tesera.feature.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.tesera.core.designsystem.theme.AppTheme
import com.tesera.core.ui.NavigationTree

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) },
    ) {
        it.calculateBottomPadding()
        DashboardNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val bottomNavItems = listOf(
        BottomNavItem(
            name = stringResource(id = R.string.title_home),
            route = NavigationTree.Home.name,
            icon = Icons.Rounded.Home,
        ),
        BottomNavItem(
            name = stringResource(id = R.string.title_search),
            route = NavigationTree.Search.name,
            icon = Icons.Rounded.Search,
        ),
        BottomNavItem(
            name = stringResource(id = R.string.title_profile),
            route = NavigationTree.Profile.name,
            icon = Icons.Rounded.Person,
        )
    )

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination

    val bottomBarDestination = bottomNavItems.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(containerColor = AppTheme.colors.primaryBackground) {
            bottomNavItems.forEach { item ->
                val selected = currentDestination?.hierarchy
                    ?.any { it.route == currentDestination.route } == true

                NavigationBarItem(
                    selected = selected,
                    onClick = {
                        val navOptions = navOptions {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        navController.navigate(item.route, navOptions)
                    },
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
    }
}