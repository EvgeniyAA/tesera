package com.tesera.feature.media.models

import com.tesera.core.mvi.UiIntent
import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel

sealed class MediaIntent : UiIntent {
    data class GetMedia(val alias: String, val linksLimit: Int, val filesLimit: Int) : MediaIntent()
    data class LinkClicked(val link: LinkModel) : MediaIntent()
    data class FileClicked(val file: FileModel) : MediaIntent()
    data class UnselectFile(val fileModel: FileModel): MediaIntent()
    object ActionInvoked : MediaIntent()
    data class StartDownload(val fileModel: FileModel) : MediaIntent()
}