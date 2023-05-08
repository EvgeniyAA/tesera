package com.tesera.data.storage.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tesera.data.storage.db.Converters
import com.tesera.domain.model.DownloadStatus
import com.tesera.domain.model.FileModel

@Entity(tableName = "files")
@TypeConverters(Converters::class)
data class FileEntity(
    @PrimaryKey val teseraId: Int,
    val objectType: String,
    val title: String,
    val content: String,
    val photoUrl: String,
    val modificationDateUtc: String,
    val creationDateUtc: String,
    val author: AuthorEntity,
    val downloadStatus: DownloadStatus,
    val alias: String,
    val isSelected: Boolean,
)

fun FileEntity?.toModel() = FileModel(
    teseraId = this?.teseraId ?: 0,
    objectType = this?.objectType.orEmpty(),
    title = this?.title.orEmpty(),
    content = this?.content.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    modificationDateUtc = this?.modificationDateUtc.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    author = this?.author.toModel(),
    alias = this?.alias.orEmpty(),
    downloadStatus = this?.downloadStatus ?: DownloadStatus.CanBeDownloaded,
    isSelected = this?.isSelected ?: false
)

fun FileModel?.toEntity() = FileEntity(
    teseraId = this?.teseraId ?: 0,
    objectType = this?.objectType.orEmpty(),
    title = this?.title.orEmpty(),
    content = this?.content.orEmpty(),
    photoUrl = this?.photoUrl.orEmpty(),
    modificationDateUtc = this?.modificationDateUtc.orEmpty(),
    creationDateUtc = this?.creationDateUtc.orEmpty(),
    author = this?.author.toEntity(),
    downloadStatus = this?.downloadStatus ?: DownloadStatus.CanBeDownloaded,
    alias = this?.alias.orEmpty(),
    isSelected = this?.isSelected ?: false
)