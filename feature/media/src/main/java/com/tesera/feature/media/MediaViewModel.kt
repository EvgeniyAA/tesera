package com.tesera.feature.media

import android.os.Environment
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.tesera.core.model.ContextWorker
import com.tesera.core.mvi.MviViewModel
import com.tesera.core.mvi.Reducer
import com.tesera.core.mvi.TimeCapsule
import com.tesera.core.utils.getFileExtension
import com.tesera.domain.media.MediaPartialState
import com.tesera.domain.media.MediaUseCase
import com.tesera.domain.model.DownloadStatus
import com.tesera.domain.model.FileModel
import com.tesera.feature.media.models.MediaAction
import com.tesera.feature.media.models.MediaIntent
import com.tesera.feature.media.models.MediaViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaUseCase: MediaUseCase,
    private val contextWorker: ContextWorker,
) : MviViewModel<MediaViewState, MediaIntent>() {
    private val reducer = MediaReducer(MediaViewState())

    override val state: StateFlow<MediaViewState>
        get() = reducer.state

    override fun obtainIntent(intent: MediaIntent) {
        reducer.sendIntent(intent)
    }

    val timeMachine: TimeCapsule<MediaViewState>
        get() = reducer.timeCapsule

    private inner class MediaReducer(initial: MediaViewState) :
        Reducer<MediaViewState, MediaIntent>(initial) {
        override fun reduce(oldState: MediaViewState, intent: MediaIntent) = when (intent) {
            MediaIntent.ActionInvoked -> setState(oldState.copy(action = MediaAction.None))
            is MediaIntent.FileClicked -> {
                unselectFile()
                selectFile(intent.file)
            }
            is MediaIntent.GetMedia -> {
                viewModelScope.launch {
                    mediaUseCase.links().combine(mediaUseCase.files()) { links, files ->
                        links to files
                    }.collect {
                        val newState = reduceStateWithMedia(
                            oldState,
                            it.first as MediaPartialState.LinksData,
                            it.second as MediaPartialState.FilesData
                        )
                        setState(newState)
                    }
                }
                getMedia(intent)
            }
            is MediaIntent.LinkClicked -> setState(oldState)
            is MediaIntent.StartDownload -> downloadFile(intent.fileUrl)
            MediaIntent.UnselectFile -> unselectFile()
        }

        private fun getMedia(
            intent: MediaIntent.GetMedia,
        ) {
            viewModelScope.launch {
                mediaUseCase.getMedia(intent.alias, intent.linksLimit, intent.filesLimit)
            }
        }

        private fun downloadFile(fileUrl: String) {
            // open and rename
            val ext = fileUrl.toUri().getFileExtension(contextWorker.getContext()) ?: "tmp"
            viewModelScope.launch { mediaUseCase.downloadFile(ext) }
        }

        private fun selectFile(file: FileModel) {
            viewModelScope.launch { mediaUseCase.selectFile(file) }
        }

        private fun unselectFile() {
            viewModelScope.launch { mediaUseCase.unselectFile() }
        }

        private fun reduceStateWithMedia(
            state: MediaViewState,
            links: MediaPartialState.LinksData,
            files: MediaPartialState.FilesData,
        ): MediaViewState {
            return state.copy(
                isLoading = files.isLoading,
                files = files.data.checkFiles(),
                filesError = files.error?.message,
                links = links.data,
                linksError = links.error?.message
            )
        }

        private fun List<FileModel>.checkFiles(): List<FileModel> = this.map {
            if (it.downloadStatus == DownloadStatus.CanBeDownloaded) {
                val fileName = "${it.title}.${
                    (it.photoUrl.toUri().getFileExtension(contextWorker.getContext())) ?: "tmp"
                }"
                val file = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    fileName
                )
                when {
                    file.exists() -> {
                        it.copy(downloadStatus = DownloadStatus.Downloaded(file.absolutePath))
                    }
                    else -> it
                }
            } else it
        }
    }
}