package com.tesera.feature.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tesera.core.ui.DisplayViewLoading
import com.tesera.core.ui.NavigationTree
import com.tesera.feature.login.models.LoginIntent
import com.tesera.feature.login.models.LoginViewState
import com.tesera.feature.login.views.LoginViewDisplay

@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val viewState = loginViewModel.loginViewState.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (val state = viewState.value) {
            LoginViewState.Loading -> DisplayViewLoading()
            is LoginViewState.Display -> LoginViewDisplay(
                onLoginClick = { username, password ->
                    loginViewModel.obtainIntent(LoginIntent.LoginClick(username, password))
                },
                onSkipLoginClick = {}
            )
            is LoginViewState.Error -> Unit
            LoginViewState.ToHome -> LaunchedEffect(state) {
                navController.navigate(NavigationTree.Dashboard.name) {
                    popUpTo(NavigationTree.Login.name) { inclusive = true }
                }
            }
        }
    }

    LaunchedEffect(key1 = viewState, block = {
        loginViewModel.obtainIntent(intent = LoginIntent.EnterScreen)
    })
}