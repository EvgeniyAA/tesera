package com.tesera.base.newsdetails

import android.text.method.LinkMovementMethod
import android.widget.TextView
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.tesera.base.newsdetails.models.NewsDetailsIntent
import com.tesera.core.utils.CoilImageGetter
import com.tesera.designsystem.theme.components.TeseraToolbar
import com.tesera.domain.model.NewsType
import com.tesera.feature.newsdetails.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsDetailsScreen(
    newsType: NewsType,
    alias: String,
    onBack: () -> Unit,
    viewModel: NewsDetailsViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = newsType, key2 = alias) {
        viewModel.obtainIntent(NewsDetailsIntent.GetNewsDetails(newsType, alias))
    }

    val viewState = viewModel.state.collectAsState()
    val details = viewState.value.newsDetails
    val title = when (newsType) {
        NewsType.News -> stringResource(id = R.string.news)
        NewsType.Article -> stringResource(id = R.string.article)
        NewsType.Thought -> stringResource(id = R.string.thought)
        NewsType.Journal -> stringResource(id = R.string.journal)
        NewsType.None -> null
    }

    Scaffold(
        modifier = Modifier.fillMaxHeight(),
        topBar = {
            TeseraToolbar(
                titleText = details?.news?.title ?: title ?: "",
                timeMachine = viewModel.timeMachine,
                navAction = onBack
            )
        }
    )
    {
        val refreshing by remember { mutableStateOf(false) }
        val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
            viewModel.obtainIntent(NewsDetailsIntent.GetNewsDetails(newsType, alias))
        })
        Box(
            modifier = Modifier
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                item { details?.news?.content?.let { HtmlText(html = it) } }
            }
            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = {
            it.text = HtmlCompat.fromHtml(
                html, HtmlCompat.FROM_HTML_MODE_COMPACT,
                CoilImageGetter(it),
                null
            )
            it.movementMethod = LinkMovementMethod.getInstance()
        }
    )
}