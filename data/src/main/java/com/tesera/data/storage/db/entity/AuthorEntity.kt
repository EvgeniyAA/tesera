package com.tesera.data.storage.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tesera.domain.model.Author

@Entity(tableName = "authors")
data class AuthorEntity(
    @PrimaryKey val teseraId: Int,
    val id: Int,
    val login: String,
    val name: String,
    val avatarUrl: String,
    val rating: Int,
    val teseraUrl: String,
)

fun AuthorEntity?.toModel() = Author(
    teseraId = this?.teseraId ?: 0,
    id = this?.id ?: 0,
    login = this?.login.orEmpty(),
    name = this?.name.orEmpty(),
    avatarUrl = this?.avatarUrl.orEmpty(),
    rating = this?.rating ?: 0,
    teseraUrl = this?.teseraUrl.orEmpty()
)

fun Author?.toEntity() = AuthorEntity(
    teseraId = this?.teseraId ?: 0,
    id = this?.id ?: 0,
    login = this?.login.orEmpty(),
    name = this?.name.orEmpty(),
    avatarUrl = this?.avatarUrl.orEmpty(),
    rating = this?.rating ?: 0,
    teseraUrl = this?.teseraUrl.orEmpty()
)