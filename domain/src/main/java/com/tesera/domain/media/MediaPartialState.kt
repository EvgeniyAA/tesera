package com.tesera.domain.media

import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel

sealed class MediaPartialState {
    data class Files(val files: List<FileModel> = emptyList()) : MediaPartialState()
    object FilesLoading : MediaPartialState()
    data class FilesError(val error: Throwable?) : MediaPartialState()

    data class Links(val links: List<LinkModel> = emptyList()) : MediaPartialState()
    object LinksLoading : MediaPartialState()
    data class LinksError(val error: Throwable?) : MediaPartialState()

    fun partial() = this
}
