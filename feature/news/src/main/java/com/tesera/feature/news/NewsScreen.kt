package com.tesera.feature.news

import androidx.compose.foundation.layout.*
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
import com.tesera.designsystem.theme.components.NewsPreviewContent
import com.tesera.designsystem.theme.components.TeseraToolbar
import com.tesera.domain.model.NewsPreview
import timber.log.Timber

@Composable
fun NewsScreen(
    onBack: () -> Unit,
    onDetailsScreen: (NewsPreview) -> Unit,
    onUserClicked: (String) -> Unit,
    viewModel: NewsViewModel = hiltViewModel(),
) {
    Scaffold(modifier = Modifier.fillMaxHeight(),
        topBar = { TeseraToolbar(titleText = stringResource(id = R.string.news_title), navAction = onBack) }
    ) {
        val news = viewModel.news.collectAsLazyPagingItems()
        NewsList(news, paddingValues = { it }, onDetailsScreen, onUserClicked)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsList(
    news: LazyPagingItems<NewsPreview>,
    paddingValues: () -> PaddingValues,
    onDetailsScreen: (NewsPreview) -> Unit,
    onUserClicked: (String) -> Unit,
) {
    val refreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {})

    Box(
        modifier = Modifier
            .padding(paddingValues())
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(news, key = { key -> key.objectId }) {
                it?.let {
                    NewsPreviewContent(
                        news = it,
                        onClick = onDetailsScreen,
                        onUserClicked = onUserClicked
                    )
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