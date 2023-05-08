package com.tesera.domain.media

import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class MediaUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
) {
    suspend fun links(alias: String, linksLimit: Int): Flow<MediaPartialState> =
        mediaRepository.links(alias, linksLimit)
            .map { MediaPartialState.Links(it) }
            .onStart { MediaPartialState.LinksLoading(true) }
            .catch { MediaPartialState.LinksError(it) }
            .onCompletion { MediaPartialState.LinksLoading(false) }

    suspend fun files(alias: String, filesLimit: Int): Flow<MediaPartialState> =
        mediaRepository.files(alias, filesLimit)
            .map { MediaPartialState.Files(it) }
            .onStart { MediaPartialState.FilesLoading(true) }
            .catch {
                Timber.e(it)
                MediaPartialState.FilesError(it) }
            .onCompletion { MediaPartialState.FilesLoading(false) }

    suspend fun media(alias: String, filesLimit: Int, linksLimit: Int) =
        merge(links(alias, linksLimit), files(alias, filesLimit))

    suspend fun selectFile(file: FileModel) = mediaRepository.selectFile(file)
    suspend fun unselectFile(fileModel: FileModel) = mediaRepository.unselectFile(fileModel)
    suspend fun downloadFile(fileModel: FileModel) = mediaRepository.downloadFile(fileModel)
}