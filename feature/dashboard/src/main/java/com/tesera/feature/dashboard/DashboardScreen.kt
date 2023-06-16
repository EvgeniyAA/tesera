package com.tesera.feature.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.tesera.core.ui.NavigationTree
import com.tesera.designsystem.theme.AppTheme

@Composable
fun DashboardScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        bottomBar = { BottomBar(navController) },
    ) {
        Box(modifier = Modifier.padding(it)) {
            DashboardNavGraph(navController)
        }
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
            route = NavigationTree.MyProfile.name,
            icon = Icons.Rounded.Person,
        )
    )

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination

    val bottomBarDestination = bottomNavItems.any { it.route == currentDestination?.route }
    AnimatedVisibility(
        visible = bottomBarDestination, enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {

        NavigationBar(containerColor = AppTheme.colors.primaryBackground) {
            bottomNavItems.forEach { item ->
                val selected = currentDestination?.hierarchy
                    ?.any { it.route == item.route } == true

                val contentColor =
                    if (selected) AppTheme.colors.primaryTextColor else AppTheme.colors.secondaryTextColor
                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = AppTheme.colors.interactiveBackground,
                    ),
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
                            color = contentColor
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = "${item.name} Icon",
                            tint = contentColor
                        )
                    }
                )
            }
        }
    }
}