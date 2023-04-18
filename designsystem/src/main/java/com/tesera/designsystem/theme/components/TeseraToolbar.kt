package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import com.tesera.designsystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeseraToolbar(
    title: String,
    description: String,
    navAction: () -> Unit,
): @Composable () -> Unit = {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    style = AppTheme.typography.heading4,
                    color = AppTheme.colors.lightTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = description,
                    style = AppTheme.typography.bodyRegular,
                    color = AppTheme.colors.lightTextColor
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = navAction) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "back",
                    tint = AppTheme.colors.lightTextColor
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = AppTheme.colors.secondaryBackground)
    )
}