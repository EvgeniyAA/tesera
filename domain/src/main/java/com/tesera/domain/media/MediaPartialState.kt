package com.tesera.domain.media

import com.tesera.domain.model.GameFile
import com.tesera.domain.model.Link

sealed class MediaPartialState {
    data class Files(val gameFiles: List<GameFile> = emptyList()) : MediaPartialState()
    object FilesLoading : MediaPartialState()
    data class FilesError(val error: Throwable?) : MediaPartialState()

    data class Links(val links: List<Link> = emptyList()) : MediaPartialState()
    object LinksLoading : MediaPartialState()
    data class LinksError(val error: Throwable?) : MediaPartialState()

    fun partial() = this
}
