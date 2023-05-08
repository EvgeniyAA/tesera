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
import kotlinx.coroutines.flow.combine
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
                unselectFile(intent.file)
                selectFile(intent.file)
            }
            is MediaIntent.GetMedia -> {
                viewModelScope.launch {
                    var oldUpdatedState = oldState
                    mediaUseCase.media(intent.alias, intent.filesLimit, intent.linksLimit).collect {
                        val newState = reduceStateWithMedia(oldUpdatedState, it)
                        oldUpdatedState = newState
                        setState(newState)
                    }
                }
                Unit
            }
            is MediaIntent.LinkClicked -> setState(oldState)
            is MediaIntent.StartDownload -> downloadFile(intent.fileModel)
            is MediaIntent.UnselectFile -> unselectFile(intent.fileModel)
        }

        private fun downloadFile(fileModel: FileModel) {
            viewModelScope.launch { mediaUseCase.downloadFile(fileModel) }
        }

        private fun selectFile(file: FileModel) {
            viewModelScope.launch { mediaUseCase.selectFile(file) }
        }

        private fun unselectFile(fileModel: FileModel) {
            viewModelScope.launch { mediaUseCase.unselectFile(fileModel) }
        }

        private fun reduceStateWithMedia(
            state: MediaViewState,
            partialState: MediaPartialState
        ): MediaViewState {
            return state.copy(
                filesLoading = (partialState as? MediaPartialState.FilesLoading)?.isLoading ?: state.filesLoading,
                files = (partialState as? MediaPartialState.Files)?.files ?: state.files,
                filesError = (partialState as? MediaPartialState.FilesError)?.error?.message ?: state.filesError,
                links = (partialState as? MediaPartialState.Links)?.links ?: state.links,
                linksError = (partialState as? MediaPartialState.LinksError)?.error?.message ?: state.linksError,
                linksLoading = (partialState as? MediaPartialState.LinksLoading)?.isLoading ?: state.linksLoading
            )
        }
    }
}