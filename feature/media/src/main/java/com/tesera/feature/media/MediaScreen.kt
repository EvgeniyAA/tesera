package com.tesera.feature.media

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.tesera.designsystem.theme.components.MediaItem
import com.tesera.designsystem.theme.components.Separator
import com.tesera.designsystem.theme.components.TeseraToolbar
import com.tesera.domain.model.FileModel
import com.tesera.feature.media.models.MediaIntent
import com.tesera.feature.media.models.MediaViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaScreen(
    alias: String,
    linksLimit: Int,
    filesLimit: Int,
    navController: NavController,
    mediaViewModel: MediaViewModel = hiltViewModel(),
) {
    val modalBottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false)
    )
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetData = remember { mutableStateOf<FileModel?>(null) }

    LaunchedEffect(key1 = alias) {
        mediaViewModel.obtainIntent(MediaIntent.GetMedia(alias, linksLimit, filesLimit))

    }
    DisposableEffect(key1 = Unit) {
        onDispose {
            mediaViewModel.obtainIntent(MediaIntent.ActionInvoked)
        }
    }

    val state by mediaViewModel.state.collectAsState()
    handleState(mediaViewModel, state, bottomSheetData, modalBottomSheetState)

    BottomSheetOptionsScreen(
        bottomSheetData.value,
        modalBottomSheetState,
        onStartDownload = { mediaViewModel.obtainIntent(MediaIntent.StartDownload(it)) }
    ) {
        Scaffold(modifier = Modifier.fillMaxHeight(),
            topBar = TeseraToolbar(
                title = stringResource(id = R.string.publications_and_files),
                timeMachine = mediaViewModel.timeMachine
            ) { navController.popBackStack() }
        ) {
            Box(modifier = Modifier.padding(it)) {
                val listState = rememberLazyListState()
                LazyColumn(state = listState) {
                    items(state.files, key = { file -> file.teseraId }) { file ->
                        MediaItem(R.drawable.ic_file, file.title) {
                            mediaViewModel.obtainIntent(MediaIntent.FileClicked(file))
                        }
                    }
                    item { Separator() }

                    items(state.links, key = { links -> links.teseraId }) { link ->
                        MediaItem(icon = R.drawable.ic_link, title = link.title) {
//                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.photoUrl))
//                            context.startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun handleState(
    mediaViewModel: MediaViewModel,
    state: MediaViewState,
    bottomSheetData: MutableState<FileModel?>,
    modalBottomSheetState: BottomSheetScaffoldState,
) {
    val selectedFile = state.files.firstOrNull { it.isSelected }
    when {
        selectedFile != null -> {
            LaunchedEffect(key1 = selectedFile) {
                bottomSheetData.value = selectedFile
                modalBottomSheetState.bottomSheetState.expand()
            }
        }
        selectedFile == null -> LaunchedEffect(key1 = selectedFile) {
            modalBottomSheetState.bottomSheetState.hide()
        }
    }
}
