package com.tesera.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DisplayViewError(
    modifier: Modifier = Modifier,
    retryButton: () -> Unit = {}
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = com.tesera.designsystem.theme.AppTheme.colors.primaryBackground
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Column(
                modifier = modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { Text("Error") }
                Row {
                    com.tesera.designsystem.theme.components.TeseraButton(
                        text = "Retry",
                        onClick = retryButton
                    )
                }
            }
        }
    }
}

@Preview("Error Screen")
@Composable
fun ErrorScreen_Preview() {
    com.tesera.designsystem.theme.TeseraTheme {
        DisplayViewError()
    }
}