package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme

@Composable
fun AdditionLabel(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .padding(top = 4.dp),
        shape = CircleShape,
        color = Color.Black.copy(alpha = 0.2f)
    ) {
        Text(
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
            text = stringResource(id = R.string.addition),
            style = AppTheme.typography.hint,
            color = AppTheme.colors.lightTextColor,
        )
    }
}