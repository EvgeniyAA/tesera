package com.tesera.feature.home.models

sealed class HomeIntent {
    object GameListClicked : HomeIntent()
    object NewsListClicked : HomeIntent()
    object ActionInvoked : HomeIntent()
}