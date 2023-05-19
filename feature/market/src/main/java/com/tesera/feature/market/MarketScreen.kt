package com.tesera.feature.market

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tesera.designsystem.theme.components.MarketItem
import com.tesera.designsystem.theme.components.TeseraToolbar
import com.tesera.domain.market.MarketType
import com.tesera.domain.model.MarketGameItem

@Composable
fun MarketScreen(
    onBack: () -> Unit,
    onItemClicked: (String) -> Unit,
    viewModel: MarketViewModel = hiltViewModel(),
) {
    val marketItems = viewModel.marketItems.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsState()
    val title = when (state.type) {
        MarketType.Sell -> stringResource(id = R.string.people_sell)
        MarketType.Buy -> stringResource(id = R.string.people_buy)
        null -> state.user ?: stringResource(id = R.string.market)
    }

    Scaffold(modifier = Modifier.fillMaxHeight(),
        topBar = { TeseraToolbar(titleText = title, navAction = onBack) }
    ) {
        MarketList(paddingValues = { it }, marketItems, onItemClicked)
    }
}

@Composable
private fun MarketList(
    paddingValues: () -> PaddingValues,
    marketItems: LazyPagingItems<MarketGameItem>,
    onItemClicked: (String) -> Unit,
) {
    LazyColumn(Modifier.padding(paddingValues())) {
        items(marketItems, key = { key -> key.id }) {
            it?.let { item -> MarketItem(item = it) { onItemClicked(it.game.alias) } }
        }
    }
}