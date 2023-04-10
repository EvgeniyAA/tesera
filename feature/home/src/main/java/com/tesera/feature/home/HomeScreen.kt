package com.tesera.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tesera.core.ui.NavigationTree
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.GamePreviewContent
import com.tesera.designsystem.theme.components.StickyHeader
import com.tesera.domain.games.GamePreviewModel
import com.tesera.domain.news.NewsPreviewModel
import com.tesera.feature.home.models.HomeAction
import com.tesera.feature.home.models.HomeIntent
import com.tesera.feature.home.models.HomeViewState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val viewState = homeViewModel.homeViewState.collectAsState()
    Column(
        modifier = Modifier
            .background(AppTheme.colors.primaryBackground)
    ) {
        ListView(viewState.value, homeViewModel)
    }

    LaunchedEffect(key1 = viewState.value.action, block = {
        when (viewState.value.action) {
            HomeAction.None -> Unit
            is HomeAction.ToGameDetails -> Unit
            HomeAction.ToGamesList -> navController.navigate(NavigationTree.Games.name)
            HomeAction.ToNewsList -> navController.navigate(NavigationTree.Games.name)
        }
    })

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            homeViewModel.obtainIntent(HomeIntent.ActionInvoked)
        }
    })
}

@Composable
fun ListView(
    state: HomeViewState,
    homeViewModel: HomeViewModel
) {
    List(state.hotnessGames, state.news, homeViewModel)
}

@Composable
fun List(
    hotnessGames: List<GamePreviewModel>,
    news: List<NewsPreviewModel>,
    homeViewModel: HomeViewModel
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        StickyHeader(
            com.tesera.designsystem.R.drawable.ic_hotness,
            com.tesera.designsystem.R.string.hotness_title
        )
        {
            coroutineScope.launch {
                listState.animateScrollToItem(0)
            }
        }
        items(
            items = hotnessGames,
            key = { game -> game.id }
        ) {
            GamePreviewContent(it) {
                homeViewModel.obtainIntent(HomeIntent.GameListClicked)
            }
        }
        StickyHeader(
            com.tesera.designsystem.R.drawable.ic_publications,
            com.tesera.designsystem.R.string.news_title
        ) {
            coroutineScope.launch {
                listState.animateScrollToItem(hotnessGames.size + 1)
            }
        }
        items(
            items = news,
            key = { newsItem -> newsItem.teseraId }
        ) {
            GamePreviewContent(
                GamePreviewModel(
                    it.teseraId,
                    it.title,
                    "",
                    2023,
                    it.photoUrl,
                    144,
                    45,
                    8.89
                )
            ) {
                homeViewModel.obtainIntent(HomeIntent.NewsListClicked)
            }
        }
    }
}
