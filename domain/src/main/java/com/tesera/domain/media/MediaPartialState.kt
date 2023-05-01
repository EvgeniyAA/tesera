package com.tesera.domain.media

import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel

sealed class MediaPartialState {
    data class FilesData(
        val isLoading: Boolean = true,
        val data: List<FileModel> = emptyList(),
        val error: Throwable? = null,
    ) : MediaPartialState()

    data class LinksData(
        val isLoading: Boolean = true,
        val data: List<LinkModel> = emptyList(),
        val error: Throwable? = null,
    ) : MediaPartialState()
}
