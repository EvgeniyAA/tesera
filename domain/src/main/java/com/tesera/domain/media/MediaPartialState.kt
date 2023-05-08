package com.tesera.domain.media

import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel

sealed class MediaPartialState {
    data class Files(val files: List<FileModel> = emptyList()) : MediaPartialState()
    data class FilesLoading(val isLoading: Boolean) : MediaPartialState()
    data class FilesError(val error: Throwable?) : MediaPartialState()

    data class Links(val links: List<LinkModel> = emptyList()) : MediaPartialState()
    data class LinksLoading(val isLoading: Boolean) : MediaPartialState()
    data class LinksError(val error: Throwable?) : MediaPartialState()
}
