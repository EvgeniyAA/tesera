package com.tesera.designsystem.theme.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tesera.designsystem.theme.AppTheme
import com.tesera.domain.model.MarketGameItem

@Composable
fun MarketItemHorizontalBlock(
    text: String,
    items: List<MarketGameItem>,
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        style = AppTheme.typography.body1,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
    )
    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
        items(items) { item ->
            HorizontalMarketItemContent(
                modifier = modifier,
                item = item,
                onClick = onClick
            )
        }
    }
}