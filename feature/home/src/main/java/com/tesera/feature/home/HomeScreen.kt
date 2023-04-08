package com.tesera.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.GamePreviewContent
import com.tesera.designsystem.theme.components.stickyHeader
import com.tesera.domain.games.GamePreviewModel
import com.tesera.feature.home.models.HomeIntent
import com.tesera.feature.home.models.HomeViewState

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val viewState = homeViewModel.homeViewState.collectAsState()
    val hotnessGames = homeViewModel.getHotnessGames().collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .background(AppTheme.colors.primaryBackground)
    ) {
        GamesView(hotnessGames)

    }

    when (val state = viewState.value) {
        is HomeViewState.Content -> {


        }
        HomeViewState.Loading -> Unit
    }

    LaunchedEffect(key1 = viewState, block = {
        homeViewModel.obtainIntent(intent = HomeIntent.GetContent)
    })
}

@Composable
fun GamesView(hotnessGames: LazyPagingItems<GamePreviewModel>) {
    GamesList(hotnessGames)
    when (val state = hotnessGames.loadState.refresh) {
        is LoadState.Error -> Unit // todo error item
        LoadState.Loading -> Unit // todo loading
        else -> Unit
    }
    when (val state = hotnessGames.loadState.append) {
        is LoadState.Error -> Unit // todo error item
        LoadState.Loading -> Unit // todo loading
        else -> Unit
    }
}

@Composable
fun GamesList(hotnessGames: LazyPagingItems<GamePreviewModel>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp)) {
        stickyHeader(com.tesera.designsystem.R.drawable.ic_hotness, com.tesera.designsystem.R.string.hotness_title)
        items(
            items = hotnessGames,
            key = { game -> game.id }
        ) {
            it?.let { game ->
                GamePreviewContent(game)
            }
        }
        stickyHeader(com.tesera.designsystem.R.drawable.ic_hotness, com.tesera.designsystem.R.string.hotness_title)
        items(
            items = hotnessGames,
            key = { game -> game.id }
        ) {
            it?.let { game ->
                GamePreviewContent(game)
            }
        }
    }
}
