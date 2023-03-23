package com.tesera.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tesera.core.extensions.TAG
import com.tesera.core.ui.NavigationTree
import timber.log.Timber

@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val viewState = splashViewModel.splashViewState.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        when (val state = viewState.value) {
            SplashViewState.Display -> Unit
            SplashViewState.ToHome -> LaunchedEffect(state) {
                navController.navigate(NavigationTree.Dashboard.name) {
                    popUpTo(NavigationTree.Splash.name) { inclusive = true }
                }
            }
            SplashViewState.ToLogin -> LaunchedEffect(state) {
                navController.navigate(NavigationTree.Login.name) {
                    popUpTo(NavigationTree.Splash.name) { inclusive = true }
                }
            }
        }
    }
//    LaunchedEffect(key1 = Unit, block = {
//        //navController.navigate(NavigationTree.Login.name)
//    })
}