package com.tesera.domain.media

import com.tesera.domain.model.GameFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import timber.log.Timber
import javax.inject.Inject

class MediaUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
) {
    private fun links(alias: String, linksLimit: Int): Flow<MediaPartialState> = merge(
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

    private fun files(alias: String, filesLimit: Int): Flow<MediaPartialState> = merge(
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

    fun mediaFromRemote(alias: String, filesLimit: Int, linksLimit: Int) =
        merge(links(alias, linksLimit), files(alias, filesLimit))


    suspend fun selectFile(gameFile: GameFile) = mediaRepository.selectFile(gameFile)
    suspend fun unselectFile(gameFile: GameFile) = mediaRepository.unselectFile(gameFile)
    suspend fun downloadFile(gameFile: GameFile) = mediaRepository.downloadFile(gameFile)
}