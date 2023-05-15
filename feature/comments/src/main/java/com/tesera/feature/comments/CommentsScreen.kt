package com.tesera.feature.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tesera.designsystem.theme.components.Comment
import com.tesera.designsystem.theme.components.TeseraToolbar
import com.tesera.domain.model.CommentModel
import com.tesera.feature.comments.models.CommentsIntent

@Composable
fun CommentsScreen(
    onBack: () -> Unit,
    commentsViewModel: CommentsViewModel = hiltViewModel(),
) {

    val viewState = commentsViewModel.commentsViewState.collectAsState()
    val state = viewState.value
    val title = stringResource(id = R.string.comments)

    Scaffold(
        modifier = Modifier.fillMaxHeight(),
        topBar = {
            TeseraToolbar(titleText = title, navAction = onBack)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            CommentsList(commentsViewModel, state.comments)
        }
    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            commentsViewModel.obtainIntent(CommentsIntent.ActionInvoked)
        }
    }
}

@Composable
fun CommentsList(
    commentsViewModel: CommentsViewModel,
    comments: List<CommentModel>,
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(comments,
            key = { comment -> comment.id }
        ) {
            val modifier = if (it.parentId != null) Modifier.padding(start = 16.dp) else Modifier
            Comment(it, modifier, onExpandedClicked = { id ->
                commentsViewModel.obtainIntent(CommentsIntent.CommentExpanded(id))
            }, onLikeClicked = { id ->
                commentsViewModel.obtainIntent(CommentsIntent.CommentLiked(id))
            })
        }
    }
}