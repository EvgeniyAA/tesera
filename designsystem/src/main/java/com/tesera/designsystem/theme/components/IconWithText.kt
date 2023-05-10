package com.tesera.designsystem.theme.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.designsystem.theme.AppTheme

@Composable
fun RowScope.IconWithText(@DrawableRes icon: Int, iconSize: Int, text: String) {
    AsyncImage(
        model = icon,
        contentDescription = null,
        modifier = Modifier
            .size(iconSize.dp)
            .align(Alignment.CenterVertically)
    )
    Text(
        text = text,
        style = AppTheme.typography.body2,
        modifier = Modifier.align(Alignment.CenterVertically).padding(start = 4.dp, end = 6.dp)
    )
}