package com.tesera.feature.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.GamePreviewContent
import com.tesera.designsystem.theme.components.StickyHeader
import com.tesera.domain.games.GamePreviewModel
import kotlinx.coroutines.launch

@Composable
fun GamesScreen(
    navController: NavController,
    gamesViewModel: GamesViewModel = hiltViewModel()
) {
    val games = gamesViewModel.getGames().collectAsLazyPagingItems()
    Column(
        modifier = Modifier.background(AppTheme.colors.primaryBackground)
    ) {
        List(games)
    }
}

@Composable
fun List(
    games: LazyPagingItems<GamePreviewModel>
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        StickyHeader(
            R.drawable.ic_hotness,
            R.string.hotness_title
        )
        {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }
        items(
            items = games,
            key = { game -> game.id }
        ) {
            it?.let { game ->
                GamePreviewContent(game)
            }
        }
    }
}