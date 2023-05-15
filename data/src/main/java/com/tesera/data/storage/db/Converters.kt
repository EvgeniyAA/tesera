package com.tesera.data.storage.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tesera.data.storage.db.entity.AuthorEntity
import com.tesera.domain.model.DownloadStatus
import com.tesera.domain.model.GameFile
import com.tesera.domain.model.Game
import com.tesera.domain.model.Link
import com.tesera.domain.model.NewsPreview
import com.tesera.domain.model.Photo

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
    fun gameToString(value: Game): String = convertToJson(value)

    @TypeConverter
    fun gameFromString(jsonString: String): Game? = getAs(jsonString)

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

    @TypeConverter
    fun photoListToString(photos: List<Photo>): String = convertToJson(photos)

    @TypeConverter
    fun photosFromString(json: String): List<Photo> {
        val type = object : TypeToken<List<Photo>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun filesListToString(value: List<GameFile>): String = convertToJson(value)

    @TypeConverter
    fun filesFromString(json: String): List<GameFile> {
        val type = object : TypeToken<List<GameFile>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun linksListToString(value: List<Link>): String = convertToJson(value)

    @TypeConverter
    fun linksFromString(json: String): List<Link> {
        val type = object : TypeToken<List<Link>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun gamesListToString(value: List<Game>): String = convertToJson(value)

    @TypeConverter
    fun gamesFromString(json: String): List<Game> {
        val type = object : TypeToken<List<Game>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun newsPreviewListToString(value: List<NewsPreview>): String = convertToJson(value)

    @TypeConverter
    fun newsPreviewFromString(json: String): List<NewsPreview> {
        val type = object : TypeToken<List<NewsPreview>>() {}.type
        return gson.fromJson(json, type)
    }
}