package com.tesera.designsystem.theme.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
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
    gameDetailsButtonModel: GameDetailsButtonModel,
    click: (GameDetailsButtonType) -> Unit,
) {
    Card(
        modifier = Modifier
            .background(
                color = gameDetailsButtonModel.buttonStartColor,
                shape = RoundedCornerShape(8.dp)
            ), onClick = { click(gameDetailsButtonModel.type) }) {
        Box(
            modifier = Modifier
                .background(color = gameDetailsButtonModel.buttonStartColor)
                .fillMaxSize()
                .padding(horizontal = 8.dp)

        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = gameDetailsButtonModel.title,
                    color = AppTheme.colors.lightTextColor,
                )
                LabelWithBackground(
                    text = gameDetailsButtonModel.count.toString(),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            AsyncImage(
                model = gameDetailsButtonModel.image,
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

enum class GameDetailsButtonType {
    Media, Comments, HasGame, Sell, Buy, GameReports
}

data class GameDetailsButtonModel(
    val title: String,
    @DrawableRes val image: Int,
    val count: Int,
    val type: GameDetailsButtonType,
    val buttonStartColor: Color,
    val buttonEndColor: Color,
)