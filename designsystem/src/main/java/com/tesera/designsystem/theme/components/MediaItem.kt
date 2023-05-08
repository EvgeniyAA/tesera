package com.tesera.designsystem.theme.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.designsystem.theme.AppTheme

@Composable
fun MediaItem(
    @DrawableRes icon: Int,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(width = 40.dp, height = 48.dp)
                .padding(vertical = 4.dp)
        ) {
            AsyncImage(
                model = icon,
                contentDescription = title,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
            )
        }
        Text(
            text = title,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}