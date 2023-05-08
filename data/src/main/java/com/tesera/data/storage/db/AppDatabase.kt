package com.tesera.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tesera.data.storage.db.dao.FileDao
import com.tesera.data.storage.db.entity.FileEntity

@Database(entities =[FileEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fileDao(): FileDao
}