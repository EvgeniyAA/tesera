package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.theme.AppTheme

@Composable
fun DisplayViewLoading(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.Center),
            strokeWidth = 4.dp,
            color = AppTheme.colors.primaryTintColor
        )
    }
}

@Preview("Loading Screen")
@Composable
fun LoadingScreen_Preview() {
    com.tesera.designsystem.theme.TeseraTheme {
        DisplayViewLoading()
    }
}