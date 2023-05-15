package com.tesera.data.repository.remote

import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.tesera.core.model.ContextWorker
import com.tesera.core.utils.getFileExtension
import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.SEGMENT_SIZE
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toFileModel
import com.tesera.data.network.model.response.toLinkModel
import com.tesera.data.storage.db.dao.FileDao
import com.tesera.data.storage.db.entity.toEntity
import com.tesera.data.storage.db.entity.toModel
import com.tesera.domain.media.MediaRepository
import com.tesera.domain.model.DownloadStatus
import com.tesera.domain.model.GameFile
import com.tesera.domain.model.Link
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import okio.buffer
import okio.sink
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class RemoteMediaRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource,
    private val fileDao: FileDao,
    private val contextWorker: ContextWorker,
) : MediaRepository {

    private var cachedLinks = emptyList<Link>()

    private val _links = MutableStateFlow<List<Link>>(emptyList())

    override suspend fun files(alias: String, filesLimit: Int) = withContext(ioDispatcher) {
        val result =
            datasource.getFiles(alias, filesLimit).map { it.toFileModel(alias).checkFile() }
        fileDao.insertFiles(result.map { it.toEntity() })
    }

    override suspend fun localFiles(alias: String): Flow<List<GameFile>> =
        fileDao.findAllFilesByAliasAsFlow(alias)
            .distinctUntilChanged()
            .map { it.map { it.toModel() } }

    override suspend fun links(alias: String, linksLimit: Int) = withContext(ioDispatcher) {
        val result = datasource.getLinks(alias, linksLimit).map { it.toLinkModel() }
        cachedLinks = result
        _links.emit(cachedLinks)
    }

    override suspend fun localLinks(): Flow<List<Link>> = _links

    override suspend fun selectFile(gameFile: GameFile) {
        withContext(ioDispatcher) {
            fileDao.selectFile(gameFile.teseraId)
        }
    }

    override suspend fun unselectFile(gameFile: GameFile) {
        withContext(ioDispatcher) {
            fileDao.update(gameFile.copy(isSelected = false).toEntity())
        }
    }

    override suspend fun downloadFile(gameFile: GameFile) {
        withContext(ioDispatcher) {
            try {
                val response = datasource.downloadFile(gameFile)
                val body = response.body
                if (!response.isSuccessful) {
                    Timber.e(response.message)
                    gameFile.updateFileWithError(response.message)
                }
                if (body != null) {
                    val contentLength = body.contentLength()
                    val source = body.source()
                    val localFile = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        gameFile.getFileNameWithExt(contextWorker.getContext())
                    )
                    val sink = localFile.sink().buffer()
                    var totalBytesRead = 0L
                    var bytesRead: Long
                    while (source.read(sink.buffer, SEGMENT_SIZE.toLong())
                            .also { bytesRead = it } != -1L
                    ) {
                        sink.emit()
                        totalBytesRead += bytesRead
                        val progress = totalBytesRead.toFloat() / contentLength
                        val status =
                            if (progress < 1f) DownloadStatus.Downloading(progress) else
                                DownloadStatus.Downloaded(localFile.absolutePath)
                        gameFile.updateFileDownloadStatus(status)
                    }
                    sink.flush()
                } else gameFile.updateFileWithError("")
            } catch (e: Exception) {
                Timber.e(e)
                gameFile.updateFileWithError(e.message.toString())
            }
        }
    }

    private suspend fun GameFile.updateFileWithError(error: String) {
        fileDao.update(this.copy(downloadStatus = DownloadStatus.Error(error)).toEntity())
    }

    private suspend fun GameFile.updateFileDownloadStatus(downloadStatus: DownloadStatus) {
        fileDao.update(this.copy(downloadStatus = downloadStatus).toEntity())
    }

    private fun GameFile.checkFile(): GameFile {
        val fileName = getFileNameWithExt(contextWorker.getContext())
        val file = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            fileName
        )
        return when {
            file.exists() -> {
                copy(downloadStatus = DownloadStatus.Downloaded(file.absolutePath))
            }
            else -> this
        }
    }

    private fun String.fileExtension(context: Context) =
        this.toUri().getFileExtension(context) ?: ""

    private fun GameFile.getFileNameWithExt(context: Context) =
        "${title}-${teseraId}.${photoUrl.fileExtension(context)}"
}