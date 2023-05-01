package com.tesera.designsystem.theme.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.designsystem.theme.AppTheme

@Composable
fun MediaOptionItem(
    title: String,
    @DrawableRes icon: Int? = null,
    progress: Float? = null,
    onClick: () -> Unit,
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            if (icon != null) {
                AsyncImage(
                    model = icon,
                    contentDescription = title,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterEnd)
                )
            }
            if (progress != null) {
                CircularProgressIndicator(
                    progress = progress, modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterEnd),
                    color = AppTheme.colors.primaryTintColor
                )
            }
        }
    }
}