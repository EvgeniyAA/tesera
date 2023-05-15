package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme

@Composable
fun DisplayViewError(modifier: Modifier, onRetry: () -> Unit) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center)
        ) {
            Text(
                text = stringResource(id = R.string.somethin_went_wrong),
                style = AppTheme.typography.heading6,
                color = AppTheme.colors.secondaryTextColor,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )

            TeseraButton(text = stringResource(id = R.string.retry), onClick = onRetry)
        }
    }
}