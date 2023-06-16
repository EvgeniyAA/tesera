package com.tesera.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.DisplayViewError
import com.tesera.designsystem.theme.components.GamePreviewContent
import com.tesera.designsystem.theme.components.NewsPreviewContent
import com.tesera.designsystem.theme.components.StickyHeaderContent
import com.tesera.domain.model.NewsPreview
import com.tesera.feature.home.models.HomeViewState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onGameDetails: (String) -> Unit,
    onGames: () -> Unit,
    onNews: () -> Unit,
    onNewsDetails: (NewsPreview) -> Unit,
    onUserClicked: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val refreshing by remember { mutableStateOf(false) }

    val pullRefreshState =
        rememberPullRefreshState(refreshing = refreshing, onRefresh = viewModel::getContent)

    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(state = listState) {
            hotnessGames(
                onGames,
                state,
                onGameDetails,
                viewModel::getGames
            )
            latestNews(
                onNews,
                state,
                onNewsDetails,
                viewModel::getNews,
                onUserClicked
            )
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.hotnessGames(
    onGames: () -> Unit,
    state: HomeViewState,
    onGameDetails: (String) -> Unit,
    onRetry: () -> Unit,
) {

    stickyHeader {
        StickyHeaderContent(
            R.drawable.ic_hotness,
            R.string.hotness_title,
            showMoreButton = true,
            onMoreButtonClick = onGames
        )
    }

    if (state.isGamesLoading)
        item { Loading() }
    if (state.gamesLoadingError != null) {
        item { DisplayViewError(Modifier.fillMaxWidth(), onRetry) }
    }

    items(
        items = state.hotnessGames,
        key = { game -> game.id }
    ) {
        GamePreviewContent(it) { onGameDetails(it.alias) }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.latestNews(
    onNews: () -> Unit,
    state: HomeViewState,
    onNewsDetails: (NewsPreview) -> Unit,
    onRetry: () -> Unit,
    onUserClicked: (String) -> Unit,
) {
    stickyHeader {
        StickyHeaderContent(
            R.drawable.ic_publications,
            R.string.news_title,
            showMoreButton = true,
            onMoreButtonClick = onNews
        )
    }

    if (state.isNewsLoading)
        item { Loading() }
    if (state.newsLoadingError != null) {
        item { DisplayViewError(Modifier.fillMaxWidth(), onRetry) }
    }

    items(
        items = state.news,
        key = { newsItem -> newsItem.objectId }
    ) {
        NewsPreviewContent(it, onClick = onNewsDetails, onUserClicked = onUserClicked)
    }
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.padding.medium)
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(AppTheme.sizes.large)
                .align(Alignment.Center),
            strokeWidth = AppTheme.padding.xxSmall,
            color = AppTheme.colors.primaryTintColor
        )
    }
}
