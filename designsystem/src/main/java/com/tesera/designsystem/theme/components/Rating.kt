package com.tesera.designsystem.theme.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme

@Composable
fun BoxScope.Rating(rating: Double, @DrawableRes ratingBg: Int) {
    Image(
        painter = painterResource(id = ratingBg),
        contentDescription = null,
        modifier = Modifier
            .size(35.dp)
            .padding(4.dp)
    )
    Text(
        text = String.format("%.1f", rating),
        style = AppTheme.typography.hint,
        color = AppTheme.colors.hintTextColor,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(8.dp)
            .align(Alignment.Center)
    )
}

@Preview("RatingPreview")
@Composable
fun GameRating_Preview() {
    TeseraTheme {
        Box {
           Rating(rating = 9.555, ratingBg = R.drawable.game_rating_bg)
        }
    }
}

@Preview("RatingPreview2")
@Composable
fun UserRating_Preview() {
    TeseraTheme {
        Box {
            Rating(rating = 9.555, ratingBg = R.drawable.user_rating_bg)
        }
    }
}
