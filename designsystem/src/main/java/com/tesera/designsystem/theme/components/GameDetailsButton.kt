package com.tesera.designsystem.theme.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsButton(
    color: Color,
    text: String,
    @DrawableRes icon: Int,
    count: Int,
    click: () -> Unit,
) {
    Card(
        modifier = Modifier
            .background(color = color, shape = RoundedCornerShape(8.dp)), onClick = { click() }) {
        Box(
            modifier = Modifier
                .background(color = color)
                .fillMaxSize()
                .padding(horizontal = 8.dp)

        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = text,
                    color = AppTheme.colors.lightTextColor,
                )
                Surface(
                    modifier = Modifier
                        .padding(top = 4.dp),
                    shape = CircleShape,
                    color = Color.Black.copy(alpha = 0.2f)
                ) {
                    Text(
                        modifier = Modifier.padding(2.dp),
                        text = count.toString(),
                        style = AppTheme.typography.hint,
                        color = AppTheme.colors.lightTextColor,
                    )
                }
            }

            AsyncImage(
                model = icon,
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterEnd)
            )

            AsyncImage(
                model = R.drawable.ic_tesera,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .alpha(0.2f)
                    .padding(start = 8.dp)
                    .align(Alignment.CenterStart)
            )
        }
    }
}