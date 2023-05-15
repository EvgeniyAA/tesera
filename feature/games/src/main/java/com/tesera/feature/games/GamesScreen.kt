package com.tesera.feature.games

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tesera.core.ui.NavigationTree
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.GamePreviewContent
import com.tesera.designsystem.theme.components.StickyHeaderContent
import com.tesera.domain.model.GamePreview
import com.tesera.feature.games.models.GamesAction
import com.tesera.feature.games.models.GamesIntent
import kotlinx.coroutines.launch

@Composable
fun GamesScreen(
    navController: NavController,
    gamesViewModel: GamesViewModel = hiltViewModel(),
) {
    val games = gamesViewModel.getGames().collectAsLazyPagingItems()
    val viewState = gamesViewModel.gamesViewState.collectAsState()

    Column(
        modifier = Modifier.background(AppTheme.colors.primaryBackground)
    ) {
        List(games, gamesViewModel)
    }

    LaunchedEffect(key1 = viewState.value.action, block = {
        when (val action = viewState.value.action) {
            GamesAction.None -> Unit
            is GamesAction.ToGameDetails ->
                navController.navigate("${NavigationTree.GamesDetails.name}/${action.game.alias}")
        }
    })

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            gamesViewModel.obtainIntent(GamesIntent.ActionInvoked)
        }
    })
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List(
    games: LazyPagingItems<GamePreview>,
    gamesViewModel: GamesViewModel,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        stickyHeader {
            StickyHeaderContent(R.drawable.ic_hotness, R.string.hotness_title)
            {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            }
        }
        items(
            items = games,
            key = { game -> game.id }
        ) {
            it?.let { game ->
                GamePreviewContent(game) {
                    gamesViewModel.obtainIntent(GamesIntent.GameDetailsClicked(game))
                }
            }
        }
    }
}