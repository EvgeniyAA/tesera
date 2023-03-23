package com.tesera.domain.authentication

sealed class AuthenticationState {
    object Success : AuthenticationState()
    data class Error(val error: String) : AuthenticationState()
}