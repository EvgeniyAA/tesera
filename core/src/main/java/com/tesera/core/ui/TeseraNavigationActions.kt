package com.tesera.core.ui

enum class NavigationTree {
    Splash,
    Login,
    Home,
    Profile, MyProfile,
    Search,
    Dashboard,
    Games, GamesDetails,
    News, NewsDetails,
    Comments,
    Media,
    Owners,
    Market,
}

object TeseraDestinations {
    const val SPLASH_ROUTE = "splash"
    const val HOME_ROUTE = "home"
    const val PROFILE_ROUTE = "profile"
}
