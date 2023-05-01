package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.tesera.core.mvi.TimeCapsule
import com.tesera.core.mvi.UiState
import com.tesera.core.mvi.debugInputPointer
import com.tesera.designsystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeseraToolbar(
    title: String,
    description: String? = null,
    timeMachine: TimeCapsule<out UiState>? = null,
    navAction: () -> Unit,
): @Composable () -> Unit = {
    TopAppBar(
        title = {
            Column(modifier = timeMachine
                ?.let { Modifier.debugInputPointer(LocalContext.current, it) } ?: Modifier
            ) {
                Text(
                    text = title,
                    style = AppTheme.typography.heading6,
                    color = AppTheme.colors.lightTextColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (!description.isNullOrEmpty())
                    Text(
                        text = description,
                        style = AppTheme.typography.body2,
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