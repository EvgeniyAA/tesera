package com.tesera.feature.splash

sealed class SplashViewState {
    object Display : SplashViewState()
    object ToLogin : SplashViewState()
    object ToHome : SplashViewState()
}