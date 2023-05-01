package com.tesera.domain.media

import com.tesera.domain.model.FileModel
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    val links: Flow<MediaPartialState>
    val files: Flow<MediaPartialState>
    suspend fun getLinks(alias: String, limit: Int)
    suspend fun getFiles(alias: String, limit: Int)

    suspend fun selectFile(fileModel: FileModel)
    suspend fun unselectFile()
    suspend fun downloadFile(fileExtension: String)
}