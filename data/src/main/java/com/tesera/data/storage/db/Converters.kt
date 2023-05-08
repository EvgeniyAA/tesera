package com.tesera.data.storage.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.tesera.data.storage.db.entity.AuthorEntity
import com.tesera.domain.model.DownloadStatus

class Converters {

    private val gson by lazy { Gson() }

    private inline fun <reified T> getAs(json: String): T =
        gson.fromJson(json, T::class.java)

    fun <T> convertToJson(obj: T): String = gson.toJson(obj)

    @TypeConverter
    fun authorToString(modelRef: AuthorEntity): String = convertToJson(modelRef)

    @TypeConverter
    fun authorFromString(jsonString: String): AuthorEntity? = getAs(jsonString)


    @TypeConverter
    fun downloadStatusToString(modelRef: DownloadStatus): String = when (modelRef) {
        DownloadStatus.CanBeDownloaded -> "CanBeDownloaded"
        is DownloadStatus.Downloaded -> "Downloaded:${modelRef.path}"
        is DownloadStatus.Downloading -> "Downloading:${modelRef.progress}"
        is DownloadStatus.Error -> "Error:${modelRef.error}"
    }

    @TypeConverter
    fun downloadStatusFromString(jsonString: String): DownloadStatus = when {
        jsonString.startsWith("Downloaded:") -> {
            val path = jsonString.substringAfter("Downloaded:")
            DownloadStatus.Downloaded(path)
        }
        jsonString.startsWith("Downloading:") -> {
            val progress = jsonString.substringAfter("Downloading:")
            DownloadStatus.Downloading(progress.toFloat())
        }
        jsonString.startsWith("Error:") -> {
            val error = jsonString.substringAfter("Error:")
            DownloadStatus.Error(error)
        }

        else -> DownloadStatus.CanBeDownloaded
    }
}