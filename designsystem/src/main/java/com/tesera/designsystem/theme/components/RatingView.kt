package com.tesera.designsystem.theme.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme

@Composable
fun RowScope.RatingView(
    startBackgroundColor: Color,
    endBackgroundColor: Color,
    @DrawableRes icon: Int,
    rating: Double,
    votesNum: Int,
    shape: RoundedCornerShape,
) {
    Row(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(listOf(startBackgroundColor, endBackgroundColor)),
                shape = shape
            )
            .padding(8.dp)
            .weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = icon,
            contentDescription = "rating",
            modifier = Modifier.size(30.dp)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = if (rating > 0.0) String.format("%.1f", rating) else "-",
                color = AppTheme.colors.lightTextColor
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = R.drawable.ic_person,
                    contentDescription = "number of votes",
                    modifier = Modifier.size(12.dp),
                    colorFilter = ColorFilter.tint(color = AppTheme.colors.lightTextColor)
                )
                Text(
                    text = if (votesNum > 0) votesNum.toString() else "-",
                    color = AppTheme.colors.lightTextColor
                )
            }
        }
    }
}