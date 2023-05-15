package com.tesera.domain.media

import com.tesera.domain.model.GameFile
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class MediaUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
) {
    private suspend fun links(alias: String, linksLimit: Int): Flow<MediaPartialState> = merge(
        flow {
            emit(MediaPartialState.LinksLoading.partial())
            try {
                mediaRepository.links(alias, linksLimit)
            } catch (e: Exception) {
                Timber.e(e)
                emit(MediaPartialState.LinksError(e).partial())
            }
        }, mediaRepository.localLinks().map { MediaPartialState.Links(it).partial() }
    )

    private suspend fun files(alias: String, filesLimit: Int): Flow<MediaPartialState> = merge(
        flow {
            emit(MediaPartialState.FilesLoading.partial())
            try {
                mediaRepository.files(alias, filesLimit)
            } catch (e: Exception) {
                Timber.e(e)
                emit(MediaPartialState.FilesError(e).partial())
            }
        },
        mediaRepository.localFiles(alias)
            .map { MediaPartialState.Files(it).partial() }
    )

    suspend fun mediaFromRemote(alias: String, filesLimit: Int, linksLimit: Int) =
        merge(links(alias, linksLimit), files(alias, filesLimit))


    suspend fun selectFile(gameFile: GameFile) = mediaRepository.selectFile(gameFile)
    suspend fun unselectFile(gameFile: GameFile) = mediaRepository.unselectFile(gameFile)
    suspend fun downloadFile(gameFile: GameFile) = mediaRepository.downloadFile(gameFile)
}