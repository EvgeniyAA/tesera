package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme

@Composable
fun TeseraLinkButton(
    modifier: Modifier = Modifier,
    text: String,
    isProgress: Boolean = false,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = { if (!isProgress) onClick() }
    ) {

        if (isProgress) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp),
                strokeWidth = 2.dp,
                color = AppTheme.colors.primaryTintColor
            )
        } else {
            Text(
                text = text,
                style = AppTheme.typography.bodyMedium,
                color = AppTheme.colors.primaryTextColor
            )
        }
    }
}

@Preview("Link Button")
@Composable
fun TeseraLinkButton_Preview() {
    TeseraTheme {
        TeseraLinkButton(text = "test", isProgress = true) {}
    }
}