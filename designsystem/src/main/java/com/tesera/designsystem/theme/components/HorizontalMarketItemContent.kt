package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.TeseraTheme
import com.tesera.domain.model.Author
import com.tesera.domain.model.ConditionType
import com.tesera.domain.model.Game
import com.tesera.domain.model.Location
import com.tesera.domain.model.MarketGameItem
import com.tesera.domain.model.MarketItemType
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorizontalMarketItemContent(
    modifier: Modifier = Modifier,
    item: MarketGameItem,
    onClick: () -> Unit = {},
) {
    val condition = when (item.condition) {
        ConditionType.New -> R.string.game_condition_new
        ConditionType.Used -> R.string.game_condition_used
        ConditionType.Any -> R.string.game_condition_any
        else -> null
    }

    Card(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .width(100.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.interactiveBackground),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = item.game.photoUrl,
                contentDescription = item.game.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .size(100.dp)
                    .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
            )
            if (item.game.isAddition) LabelWithBackground(
                stringResource(id = R.string.addition),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                text = item.game.title, style = AppTheme.typography.body1, maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            val price = item.price ?: 0.0
            if (price > 0.0)
                Text(
                    text = item.price.toString(),
                    style = AppTheme.typography.body2,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
        }
    }
}

@Preview("HorizontalMarketItemContent")
@Composable
fun HorizontalMarketItemContent_Preview() {
    TeseraTheme {
        HorizontalMarketItemContent(
            Modifier,
            MarketGameItem(
                MarketItemType.Sales,
                "Spirit Island 1 million",
                "",
                0,
                0,
                "want to sell",
                Date(),
                5000.00,
                Location("", "Russia", "", 0, "", ""),
                Location("", "Moscow", "", 0, "", ""),
                "RUB",
                "",
                ConditionType.Used,
                Game(
                    0,
                    0,
                    0,
                    "Spirit Island",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    2022,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0.0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0, 0, 0, 0, true
                ),
                Author(1, 1, "AB", "Ivan", "", 1, "")
            ), {}
        )
    }
}