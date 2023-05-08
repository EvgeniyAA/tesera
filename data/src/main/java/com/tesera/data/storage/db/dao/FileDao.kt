package com.tesera.data.storage.db.dao

import androidx.room.*
import com.tesera.data.storage.db.entity.FileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiles(files: List<FileEntity>)

    @Query("SELECT * FROM files WHERE alias = :alias")
    fun findAllFilesByAlias(alias: String): Flow<List<FileEntity>>

    @Update
    suspend fun update(file: FileEntity)
}