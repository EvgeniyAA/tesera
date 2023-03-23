package com.tesera.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tesera.core.designsystem.theme.AppTheme
import com.tesera.core.designsystem.theme.TeseraTheme
import com.tesera.core.designsystem.theme.components.TeseraButton

@Composable
fun DisplayViewError(
    modifier: Modifier = Modifier,
    retryButton: () -> Unit = {}
) {

    Surface(
        modifier = modifier.fillMaxSize(),
        color = AppTheme.colors.primaryBackground
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
                    TeseraButton(text = "Retry", onClick = retryButton)
                }
            }
        }
    }
}

@Preview("Error Screen")
@Composable
fun ErrorScreen_Preview() {
    TeseraTheme {
        DisplayViewError()
    }
}