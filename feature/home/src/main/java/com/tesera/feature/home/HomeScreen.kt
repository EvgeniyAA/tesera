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
import com.tesera.designsystem.theme.components.NewsPreviewContent
import com.tesera.designsystem.theme.components.StickyHeader
import com.tesera.domain.model.GamePreviewModel
import com.tesera.domain.model.NewsPreviewModel
import com.tesera.feature.home.models.HomeAction
import com.tesera.feature.home.models.HomeIntent
import com.tesera.feature.home.models.HomeViewState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val viewState = homeViewModel.homeViewState.collectAsState()
    Column(
        modifier = Modifier
            .background(AppTheme.colors.primaryBackground)
    ) {
        ListView(viewState.value, homeViewModel)
    }

    LaunchedEffect(key1 = viewState.value.action, block = {
        when (val action = viewState.value.action) {
            HomeAction.None -> Unit
            is HomeAction.ToGameDetails ->
                navController.navigate("${NavigationTree.GamesDetails.name}/${action.game.alias}")
            HomeAction.ToGamesList -> navController.navigate(NavigationTree.Games.name)
            HomeAction.ToNewsList -> navController.navigate(NavigationTree.News.name)
            is HomeAction.ToNewsDetails -> navController.navigate("${NavigationTree.NewsDetails.name}/${action.news.objectType.name}/${action.news.alias}")
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
    homeViewModel: HomeViewModel,
) {
    List(state.hotnessGames, state.news, homeViewModel)
}

@Composable
fun List(
    hotnessGames: List<GamePreviewModel>,
    news: List<NewsPreviewModel>,
    homeViewModel: HomeViewModel,
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(state = listState) {
        StickyHeader(
            com.tesera.designsystem.R.drawable.ic_hotness,
            com.tesera.designsystem.R.string.hotness_title,
            showMoreButton = true,
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            onMoreButtonClick = {
                homeViewModel.obtainIntent(HomeIntent.GameListClicked)
            }
        )

        items(
            items = hotnessGames,
            key = { game -> game.id }
        ) {
            GamePreviewContent(it) { game ->
                homeViewModel.obtainIntent(HomeIntent.GameDetailsClicked(game))
            }
        }
        StickyHeader(
            com.tesera.designsystem.R.drawable.ic_publications,
            com.tesera.designsystem.R.string.news_title,
            showMoreButton = true,
            onClick = {
                coroutineScope.launch {
                    listState.animateScrollToItem(hotnessGames.size + 1)
                }
            },
            onMoreButtonClick = {
                homeViewModel.obtainIntent(HomeIntent.NewsListClicked)
            }
        )

        items(
            items = news,
            key = { newsItem -> newsItem.objectId }
        ) {
            NewsPreviewContent(it) {
                homeViewModel.obtainIntent(HomeIntent.NewsDetailsClicked(it))
            }
        }
    }
}
