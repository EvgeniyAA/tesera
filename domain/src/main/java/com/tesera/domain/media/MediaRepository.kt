package com.tesera.domain.media

import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun links(alias: String, linksLimit: Int)
    suspend fun localLinks(): Flow<List<LinkModel>>
    suspend fun files(alias: String, filesLimit: Int)
    suspend fun localFiles(alias: String): Flow<List<FileModel>>

    suspend fun selectFile(fileModel: FileModel)
    suspend fun unselectFile(fileModel: FileModel)
    suspend fun downloadFile(fileModel: FileModel)
}