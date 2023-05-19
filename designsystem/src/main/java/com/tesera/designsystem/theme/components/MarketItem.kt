package com.tesera.designsystem.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tesera.core.utils.niceDateStr
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
fun MarketItem(
    item: MarketGameItem,
    onClick: () -> Unit,
) {
    val condition = when (item.condition) {
        ConditionType.New -> R.string.game_condition_new
        ConditionType.Used -> R.string.game_condition_used
        ConditionType.Any -> R.string.game_condition_any
        else -> null
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AppTheme.colors.interactiveBackground)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                ) {
                    AsyncImage(
                        model = item.game.photoUrl,
                        contentDescription = item.game.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(72.dp)
                            .align(Alignment.CenterVertically)
                            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
                    )
                    Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                        Text(
                            text = item.game.title,
                            style = AppTheme.typography.body1,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (item.author.name.isNotEmpty()) {
                            Row(modifier = Modifier.padding(top = 4.dp)) {

                                Avatar(author = item.author, avatarSize = 20F)
                                Text(
                                    text = item.author.name,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = AppTheme.typography.body2,
                                    modifier = Modifier
                                        .padding(start = 2.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, end = 8.dp, bottom = 8.dp)
                ) {
                    Row(modifier = Modifier.align(Alignment.BottomEnd)) {
                        if (condition != null)
                            IconWithText(
                                icon = R.drawable.ic_game_condition,
                                iconSize = 16,
                                text = stringResource(condition)
                            )

                        if (item.price != null) {
                            IconWithText(
                                icon = R.drawable.ic_rub,
                                iconSize = 16,
                                text = "${item.price} ${item.currency}"
                            )
                        }
                        if (item.country.value.isNotEmpty() && item.city.value.isNotEmpty())
                            IconWithText(
                                icon = R.drawable.ic_location,
                                iconSize = 14,
                                text = "${item.country.value}, ${item.city.value}"
                            )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = item.publicationDateUtc.niceDateStr(),
                        style = AppTheme.typography.body2,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
}

@Preview("MarketItemPreview")
@Composable
fun MarketItem_Preview() {
    TeseraTheme {
        MarketItem(
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
                    "",
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
                    0, 0, 0, 0, false
                ),
                Author(1, 1, "AB", "Ivan", "", 1, "")
            ), {}
        )
    }
}