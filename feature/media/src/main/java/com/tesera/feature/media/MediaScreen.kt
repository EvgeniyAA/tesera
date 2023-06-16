package com.tesera.feature.media

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tesera.designsystem.theme.AppTheme
import com.tesera.designsystem.theme.components.MediaItem
import com.tesera.designsystem.theme.components.Separator
import com.tesera.designsystem.theme.components.TeseraToolbar
import com.tesera.domain.model.MediaModel
import com.tesera.feature.media.models.MediaIntent
import com.tesera.feature.media.models.MediaViewState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaScreen(
    alias: String,
    linksLimit: Int,
    filesLimit: Int,
    onBack: () -> Unit,
    mediaViewModel: MediaViewModel = hiltViewModel(),
) {
    val bottomSheetData = remember { mutableStateOf<MediaModel?>(null) }

    LaunchedEffect(key1 = alias) {
        mediaViewModel.obtainIntent(MediaIntent.GetMedia(alias, linksLimit, filesLimit))
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            mediaViewModel.obtainIntent(MediaIntent.ActionInvoked)
        }
    }

    val state by mediaViewModel.state.collectAsState()


    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    HandleState(mediaViewModel, state, bottomSheetData, modalBottomSheetState)

    val title = stringResource(id = R.string.publications_and_files)
    ModalBottomSheet(
        bottomSheetData.value,
        modalBottomSheetState,
        onStartDownload = { mediaViewModel.obtainIntent(MediaIntent.StartDownload(it)) }
    ) {
        Scaffold(modifier = Modifier.fillMaxHeight(),
            topBar = {
                TeseraToolbar(
                    titleText = title,
                    timeMachine = mediaViewModel.timeMachine,
                    navAction = onBack
                )
            }
        ) {
            MediaScreenContent(
                mediaViewModel = mediaViewModel,
                state = state,
                alias = alias,
                linksLimit = linksLimit,
                filesLimit = filesLimit,
                paddingValues = it
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MediaScreenContent(
    mediaViewModel: MediaViewModel,
    state: MediaViewState,
    alias: String,
    linksLimit: Int,
    filesLimit: Int,
    paddingValues: PaddingValues,
) {
    val refreshing by remember { mutableStateOf(false) }

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = {
        mediaViewModel.obtainIntent(MediaIntent.GetMedia(alias, linksLimit, filesLimit, true))
    })

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .pullRefresh(pullRefreshState)
    ) {
        val listState = rememberLazyListState()
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(state.gameFiles, key = { file -> file.teseraId }) { file ->
                MediaItem(R.drawable.ic_file, file.title) {
                    mediaViewModel.obtainIntent(MediaIntent.MediaClicked(file))
                }
            }
            if (state.filesLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        contentAlignment = Alignment.Center
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
            }
            item { Separator() }

            items(state.links, key = { links -> links.teseraId }) { link ->
                MediaItem(icon = R.drawable.ic_link, title = link.title) {
                    mediaViewModel.obtainIntent(MediaIntent.MediaClicked(link))
                }
            }
            if (state.linksLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        contentAlignment = Alignment.Center
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
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HandleState(
    mediaViewModel: MediaViewModel,
    state: MediaViewState,
    bottomSheetData: MutableState<MediaModel?>,
    modalBottomSheetState: ModalBottomSheetState,
) {
    val selectedMedia = state.selectedMedia
    LaunchedEffect(selectedMedia) {
        bottomSheetData.value = selectedMedia
        if (selectedMedia == null) modalBottomSheetState.hide() else modalBottomSheetState.show()
    }

    LaunchedEffect(key1 = modalBottomSheetState.isVisible) {
        if (!modalBottomSheetState.isVisible && selectedMedia != null)
            mediaViewModel.obtainIntent(MediaIntent.MediaUnselected(selectedMedia))
    }
}
