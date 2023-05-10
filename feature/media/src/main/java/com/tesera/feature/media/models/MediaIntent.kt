package com.tesera.feature.media.models

import com.tesera.core.mvi.UiIntent
import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel
import com.tesera.domain.model.MediaModel

sealed class MediaIntent : UiIntent {
    data class GetMedia(
        val alias: String,
        val linksLimit: Int,
        val filesLimit: Int,
        val fromRefresh: Boolean = false,
    ) : MediaIntent()
    data class MediaClicked(val media: MediaModel) : MediaIntent()
    data class MediaUnselected(val media: MediaModel) : MediaIntent()
    object ActionInvoked : MediaIntent()
    data class StartDownload(val fileModel: FileModel) : MediaIntent()
}