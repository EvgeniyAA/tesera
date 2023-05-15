package com.tesera.domain.media

import com.tesera.domain.model.GameFile
import com.tesera.domain.model.Link
import kotlinx.coroutines.flow.Flow

interface MediaRepository {
    suspend fun links(alias: String, linksLimit: Int)
    suspend fun localLinks(): Flow<List<Link>>
    suspend fun files(alias: String, filesLimit: Int)
    suspend fun localFiles(alias: String): Flow<List<GameFile>>

    suspend fun selectFile(gameFile: GameFile)
    suspend fun unselectFile(gameFile: GameFile)
    suspend fun downloadFile(gameFile: GameFile)
}