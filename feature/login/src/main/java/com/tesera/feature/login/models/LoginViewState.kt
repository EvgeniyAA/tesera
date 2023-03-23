package com.tesera.feature.login.models

sealed class LoginViewState {
    object Loading : LoginViewState()
    object Display : LoginViewState()
    object ToHome : LoginViewState()
    data class Error(val error: String) : LoginViewState()
}