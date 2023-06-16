package com.tesera.feature.games

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.components.GamePreviewContent
import com.tesera.designsystem.theme.components.TeseraToolbar
import com.tesera.domain.model.GamePreview

@Composable
fun GamesScreen(
    onGameDetails: (String) -> Unit,
    onBack: () -> Unit,
    gamesViewModel: GamesViewModel = hiltViewModel(),
) {
//    val viewState = gamesViewModel.gamesViewState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxHeight(),
        topBar = { TeseraToolbar(titleText = stringResource(id = R.string.hotness_title), navAction = onBack) }
    ) {
        val games = gamesViewModel.games.collectAsLazyPagingItems()
        List(games, paddingValues = { it }, onGameDetails)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun List(
    games: LazyPagingItems<GamePreview>,
    paddingValues: () -> PaddingValues,
    onGameDetails: (String) -> Unit
) {
    val refreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {})

    Box(
        modifier = Modifier
            .padding(paddingValues())
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = games,
            key = { game -> game.id }
        ) {
            it?.let { game ->
                GamePreviewContent(game) { onGameDetails(game.alias) }
            }
        }
    }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}