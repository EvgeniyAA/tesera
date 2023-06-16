package com.tesera.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme

@Composable
fun SplashScreen(onDashboard: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_tesera),
            "",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(color = AppTheme.colors.primaryTintColor),
            modifier = Modifier.size(120.dp)
        )
        LaunchedEffect(Unit) { onDashboard() }
    }
}

@Preview("SplashScreen_Preview")
@Composable
fun SplashScreen_Preview() {
    TeseraTheme {
        SplashScreen {}
    }
}