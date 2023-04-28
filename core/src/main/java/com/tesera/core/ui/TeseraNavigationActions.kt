package com.tesera.core.ui

enum class NavigationTree {
    Splash,
    Login,
    Home,
    Profile,
    Search,
    Dashboard,
    Games, GamesDetails,
    Comments,
    Media
}

object TeseraDestinations {
    const val SPLASH_ROUTE = "splash"
    const val HOME_ROUTE = "home"
    const val PROFILE_ROUTE = "profile"
}
