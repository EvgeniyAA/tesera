package com.tesera.domain.media

import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MediaUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
) {
    fun links(): Flow<MediaPartialState> = mediaRepository.links
    fun files(): Flow<MediaPartialState> = mediaRepository.files
    suspend fun getMedia(alias: String, linksLimit: Int, filesLimit: Int) {
        mediaRepository.getFiles(alias, filesLimit)
        mediaRepository.getLinks(alias, linksLimit)
    }

    suspend fun getFiles(alias: String, filesLimit: Int) =
        mediaRepository.getFiles(alias, filesLimit)

    suspend fun selectFile(file: FileModel) = mediaRepository.selectFile(file)
    suspend fun unselectFile() = mediaRepository.unselectFile()
    suspend fun downloadFile(fileExtension: String) = mediaRepository.downloadFile(fileExtension)
}