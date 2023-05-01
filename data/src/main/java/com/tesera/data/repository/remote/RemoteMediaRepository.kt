package com.tesera.data.repository.remote

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.core.net.toUri
import com.tesera.data.network.Dispatcher
import com.tesera.data.network.NetworkDataSource
import com.tesera.data.network.SEGMENT_SIZE
import com.tesera.data.network.TeseraDispatchers
import com.tesera.data.network.model.response.toFileModel
import com.tesera.data.network.model.response.toLinkModel
import com.tesera.domain.media.MediaPartialState
import com.tesera.domain.media.MediaRepository
import com.tesera.domain.model.DownloadStatus
import com.tesera.domain.model.FileModel
import com.tesera.domain.model.LinkModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import okio.buffer
import okio.sink
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class RemoteMediaRepository @Inject constructor(
    @Dispatcher(TeseraDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val datasource: NetworkDataSource,
) : MediaRepository {

    private var cachedFiles = emptyList<FileModel>()
    private var cachedLinks = emptyList<LinkModel>()

    private val _files = MutableStateFlow<MediaPartialState>(MediaPartialState.FilesData())
    private val _links = MutableStateFlow<MediaPartialState>(MediaPartialState.LinksData())

    override val files: Flow<MediaPartialState>
        get() = _files
    override val links: Flow<MediaPartialState>
        get() = _links

    override suspend fun getLinks(alias: String, limit: Int): Unit =
        withContext(ioDispatcher) {
            _links.emit(MediaPartialState.LinksData(isLoading = true))
            datasource.getLinks(alias, limit).map { it.map { it.toLinkModel() } }
                .onSuccess {
                    cachedLinks = it
                    _links.emit(MediaPartialState.LinksData(data = cachedLinks, isLoading = false))
                }
                .onFailure {
                    _links.emit(MediaPartialState.LinksData(isLoading = false, error = it))
                }
        }

    override suspend fun getFiles(alias: String, limit: Int): Unit =
        withContext(ioDispatcher) {
            _files.emit(MediaPartialState.FilesData(isLoading = true))
            datasource.getFiles(alias, limit).map { it.map { it.toFileModel() } }
                .onSuccess {
                    cachedFiles = it
                    _files.emit(MediaPartialState.FilesData(data = cachedFiles, isLoading = false))
                }
                .onFailure {
                    _files.emit(MediaPartialState.FilesData(isLoading = false, error = it))
                }
        }

    override suspend fun selectFile(fileModel: FileModel) {
        withContext(ioDispatcher) {
            cachedFiles =
                cachedFiles.map { it.copy(isSelected = if (fileModel.teseraId == it.teseraId) true else it.isSelected) }
            _files.emit(MediaPartialState.FilesData(data = cachedFiles))
        }
    }

    override suspend fun unselectFile() {
        withContext(ioDispatcher) {
            cachedFiles =
                cachedFiles.map { it.copy(isSelected = false) }
            _files.emit(MediaPartialState.FilesData(data = cachedFiles))
        }
    }

    override suspend fun downloadFile(fileExtension: String) {
        withContext(ioDispatcher) {
            val file = cachedFiles.firstOrNull { it.isSelected }
            file?.let { selectedFile ->
                try {
                    val response = datasource.downloadFile(selectedFile)
                    if (!response.isSuccessful) {
                        Timber.e(response.message)
                        cachedFiles = cachedFiles.map {
                            it.copy(
                                downloadStatus = if (it.teseraId == selectedFile.teseraId)
                                    DownloadStatus.Error(response.message) else it.downloadStatus
                            )
                        }
                        _files.emit(MediaPartialState.FilesData(data = cachedFiles))
                    }
                    val contentLength = response.body?.contentLength() ?: 0
                    val source = response.body?.source()
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "${selectedFile.title}.$fileExtension"
                    )
                    val sink = file.sink().buffer()
                    var totalBytesRead = 0L
                    var bytesRead: Long
                    while (source!!.read(sink.buffer, SEGMENT_SIZE.toLong())
                            .also { bytesRead = it } != -1L
                    ) {
                        sink.emit()
                        totalBytesRead += bytesRead
                        val progress = totalBytesRead.toFloat() / contentLength
                        val status =
                            if (progress < 1f) DownloadStatus.Downloading(progress) else DownloadStatus.Downloaded(
                                file.absolutePath
                            )
                        cachedFiles = cachedFiles.map {
                            it.copy(
                                downloadStatus = if (it.teseraId == selectedFile.teseraId) status else it.downloadStatus
                            )
                        }
                        _files.emit(MediaPartialState.FilesData(data = cachedFiles))
                    }
                    sink.flush()
                } catch (e: Exception) {
                    Timber.e(e)
                    cachedFiles = cachedFiles.map {
                        it.copy(
                            downloadStatus = if (it.teseraId == selectedFile.teseraId)
                                DownloadStatus.Error(e.message.toString()) else it.downloadStatus
                        )
                    }
                    _files.emit(MediaPartialState.FilesData(data = cachedFiles))
                }
            }
        }
    }
}