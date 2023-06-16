package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.theme.AppTheme

@Composable
fun LabelWithBackground(text: String, modifier: Modifier = Modifier, backgroundColor: Color = Color.Black.copy(alpha = 0.2f)) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = backgroundColor
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
            text = text,
            style = AppTheme.typography.hint,
            color = AppTheme.colors.lightTextColor,
        )
    }
}