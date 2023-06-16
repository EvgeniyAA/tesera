package com.tesera.domain.model

import androidx.compose.runtime.Stable

@Stable
data class GameFile(
    val teseraId: Int,
    val objectType: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val modificationDateUtc: String,
    val creationDateUtc: String,
    val author: Author,
    val downloadStatus: DownloadStatus = DownloadStatus.CanBeDownloaded,
    val isSelected: Boolean = false,
    val alias: String
): MediaModel()

sealed class DownloadStatus {
    object CanBeDownloaded : DownloadStatus()
    data class Downloading(val progress: Float) : DownloadStatus()
    data class Downloaded(val path: String) : DownloadStatus()
    data class Error(val error: String) : DownloadStatus()
}