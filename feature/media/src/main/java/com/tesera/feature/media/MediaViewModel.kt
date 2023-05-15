package com.tesera.feature.media

import androidx.lifecycle.viewModelScope
import com.tesera.core.mvi.MviViewModel
import com.tesera.core.mvi.Reducer
import com.tesera.core.mvi.TimeCapsule
import com.tesera.domain.media.MediaPartialState
import com.tesera.domain.media.MediaUseCase
import com.tesera.domain.model.GameFile
import com.tesera.domain.model.Link
import com.tesera.feature.media.models.MediaAction
import com.tesera.feature.media.models.MediaIntent
import com.tesera.feature.media.models.MediaViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaUseCase: MediaUseCase,
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
            is MediaIntent.MediaClicked -> when (intent.media) {
                is GameFile -> selectFile(intent.media)
                is Link -> setState(oldState.copy(selectedMedia = intent.media))
            }
            is MediaIntent.GetMedia -> getMedia(oldState, intent)
            is MediaIntent.StartDownload -> downloadFile(intent.gameFile)
            is MediaIntent.MediaUnselected -> when (intent.media) {
                is GameFile -> unselectFile(intent.media)
                is Link -> setState(oldState.copy(selectedMedia = null))
            }
        }

        private fun getMedia(
            oldState: MediaViewState,
            intent: MediaIntent.GetMedia,
        ) {
            viewModelScope.launch {
                var oldUpdatedState = oldState
                mediaUseCase.mediaFromRemote(intent.alias, intent.filesLimit, intent.linksLimit)
                    .collect {
                        val newState = reducePartialState(oldUpdatedState, it)
                        oldUpdatedState = newState
                        setState(newState)
                    }
            }
        }

        private fun downloadFile(gameFile: GameFile) {
            viewModelScope.launch { mediaUseCase.downloadFile(gameFile) }
        }

        private fun selectFile(gameFile: GameFile) {
            viewModelScope.launch { mediaUseCase.selectFile(gameFile) }
        }

        private fun unselectFile(gameFile: GameFile) {
            viewModelScope.launch { mediaUseCase.unselectFile(gameFile) }
        }

        private fun reducePartialState(
            oldState: MediaViewState,
            partialState: MediaPartialState,
        ): MediaViewState {
            Timber.d(partialState.toString())
            return when (partialState) {
                is MediaPartialState.Files -> oldState.copy(
                    gameFiles = partialState.gameFiles,
                    filesLoading = false,
                    filesError = null,
                    selectedMedia = partialState.gameFiles.firstOrNull { it.isSelected })
                is MediaPartialState.FilesError -> oldState.copy(
                    filesLoading = false,
                    filesError = partialState.error?.message
                )
                MediaPartialState.FilesLoading -> oldState.copy(
                    filesLoading = true,
                    gameFiles = emptyList()
                )
                is MediaPartialState.Links -> oldState.copy(
                    links = partialState.links,
                    linksLoading = false,
                    linksError = null,
                    selectedMedia = null
                )
                is MediaPartialState.LinksError -> oldState.copy(
                    linksLoading = false,
                    linksError = partialState.error?.message
                )
                MediaPartialState.LinksLoading -> oldState.copy(
                    linksLoading = true,
                    links = emptyList()
                )
            }
        }
    }
}