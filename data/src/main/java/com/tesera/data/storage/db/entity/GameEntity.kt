package com.tesera.data.storage.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey val teseraId: Int,
)