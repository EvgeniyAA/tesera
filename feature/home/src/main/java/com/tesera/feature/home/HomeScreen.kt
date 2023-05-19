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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tesera.designsystem.R
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.DisplayViewError
import com.tesera.designsystem.theme.components.GamePreviewContent
import com.tesera.designsystem.theme.components.NewsPreviewContent
import com.tesera.designsystem.theme.components.StickyHeaderContent
import com.tesera.domain.model.GamePreview
import com.tesera.domain.model.NewsPreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    onGameDetails: (String) -> Unit,
    onGames: () -> Unit,
    onNews: () -> Unit,
    onNewsDetails: (NewsPreview) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val refreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        viewModel.getContent()
    })

    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        val onHeaderClick: () -> Unit = remember {
            {
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            }
        }
        val onGamesRetry: () -> Unit = remember { { viewModel.getGames() } }
        val onNewsRetry: () -> Unit = remember { { viewModel.getNews() } }
        LazyColumn(state = listState) {
            HotnessGames(
                onHeaderClick,
                onGames,
                state.hotnessGames,
                state.isGamesLoading,
                state.gamesLoadingError,
                onGameDetails,
                onGamesRetry
            )
            LatestNews(
                onHeaderClick,
                onNews,
                state.news,
                state.isNewsLoading,
                state.newsLoadingError,
                onNewsDetails,
                onNewsRetry
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
private fun LazyListScope.HotnessGames(
    onHeaderClick: () -> Unit,
    onGames: () -> Unit,
    games: List<GamePreview>,
    isLoading: Boolean,
    error: Throwable?,
    onGameDetails: (String) -> Unit,
    onRetry: () -> Unit,
) {

    stickyHeader {
        StickyHeaderContent(
            R.drawable.ic_hotness,
            R.string.hotness_title,
            showMoreButton = true,
            onClick = onHeaderClick,
            onMoreButtonClick = onGames
        )
    }

    if (isLoading)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    strokeWidth = 2.dp,
                    color = AppTheme.colors.primaryTintColor
                )
            }
        }
    if (error != null) {
        item {
            DisplayViewError(Modifier.fillMaxWidth(), onRetry)
        }
    }

    items(
        items = games,
        key = { game -> game.id }
    ) {
        GamePreviewContent(it) { onGameDetails(it.alias) }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.LatestNews(
    onHeaderClick: () -> Unit,
    onNews: () -> Unit,
    news: List<NewsPreview>,
    isLoading: Boolean,
    error: Throwable?,
    onNewsDetails: (NewsPreview) -> Unit,
    onRetry: () -> Unit,
) {
    stickyHeader {
        StickyHeaderContent(
            R.drawable.ic_publications,
            R.string.news_title,
            showMoreButton = true,
            onClick = onHeaderClick,
            onMoreButtonClick = onNews
        )
    }

    if (isLoading)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    strokeWidth = 2.dp,
                    color = AppTheme.colors.primaryTintColor
                )
            }
        }
    if (error != null) {
        item {
            DisplayViewError(Modifier.fillMaxWidth(), onRetry)
        }
    }

    items(
        items = news,
        key = { newsItem -> newsItem.objectId }
    ) { NewsPreviewContent(it, onNewsDetails) }
}
