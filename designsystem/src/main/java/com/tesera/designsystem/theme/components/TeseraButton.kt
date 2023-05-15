package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme

@Composable
fun TeseraButton(
    modifier: Modifier = Modifier,
    text: String,
    isProgress: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        onClick = { if (!isProgress) onClick() },
        shape = RoundedCornerShape(size = 24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.primaryTintColor
        )
    ) {
        if (isProgress) {
            CircularProgressIndicator(
                modifier = modifier.size(24.dp),
                strokeWidth = 2.dp,
                color = AppTheme.colors.secondaryTintColor
            )
        } else {
            Text(
                text = text,
                style = AppTheme.typography.body1,
                color = AppTheme.colors.secondaryTextColor
            )
        }
    }
}

@Preview("Button")
@Composable
fun TeseraButton_Preview() {
    TeseraTheme {
        TeseraButton(text = "test", isProgress = false) {}
    }
}