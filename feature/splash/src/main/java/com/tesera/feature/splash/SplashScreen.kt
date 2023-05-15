package com.tesera.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun SplashScreen(onDashboard: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(Unit) { onDashboard() }
    }
}