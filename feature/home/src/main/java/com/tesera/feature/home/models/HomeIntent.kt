package com.tesera.feature.home.models

sealed class HomeIntent {
    object Idle : HomeIntent()
    object GetContent : HomeIntent()
}