package com.tesera.feature.media.models

import com.tesera.core.mvi.UiState
import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel

data class MediaViewState(
    val links: List<LinkModel> = emptyList(),
    val files: List<FileModel> = emptyList(),
    val linksError: String? = null,
    val filesError: String? = null,
    val filesLoading: Boolean = false,
    val linksLoading: Boolean = false,
    val action: MediaAction = MediaAction.None
) : UiState

sealed class MediaAction {
    object None : MediaAction()
}