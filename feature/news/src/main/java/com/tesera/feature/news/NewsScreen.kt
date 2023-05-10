package com.tesera.feature.news

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.tesera.core.ui.NavigationTree
import com.tesera.designsystem.theme.components.NewsPreviewContent
import com.tesera.designsystem.theme.components.TeseraToolbar
import com.tesera.feature.news.models.NewsAction
import com.tesera.feature.news.models.NewsIntent

@Composable
fun NewsScreen(
    navController: NavController,
    viewModel: NewsViewModel = hiltViewModel(),
) {

    Scaffold(modifier = Modifier.fillMaxHeight(),
        topBar = TeseraToolbar(
            title = stringResource(id = R.string.news_title)
        ) { navController.popBackStack() }
    ) {
        NewsList(viewModel = viewModel, paddingValues = it)
    }

    val viewState = viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = viewState.value.action, block = {
        when (val action = viewState.value.action) {
            NewsAction.None -> Unit
            is NewsAction.ToNewsDetails ->
                navController.navigate("${NavigationTree.NewsDetails.name}/${action.news.objectType.name}/${action.news.alias}")

        }
    })

    DisposableEffect(key1 = Unit, effect = {
        onDispose {
            viewModel.obtainIntent(NewsIntent.ActionInvoked)
        }
    })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsList(
    viewModel: NewsViewModel,
    paddingValues: PaddingValues,
) {
    val refreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
    })

    val news = viewModel.getNews().collectAsLazyPagingItems()


    Box(
        modifier = Modifier
            .padding(paddingValues)
            .pullRefresh(pullRefreshState)
    ) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(news, key = { key -> key.objectId }) {
                it?.let {
                    NewsPreviewContent(news = it) {
                        viewModel.obtainIntent(NewsIntent.NewsDetailsClicked(it))
                    }
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