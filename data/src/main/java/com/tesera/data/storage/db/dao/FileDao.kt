package com.tesera.data.storage.db.dao

import androidx.room.*
import com.tesera.data.storage.db.entity.FileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiles(files: List<FileEntity>)

    @Query("SELECT * FROM files WHERE alias = :alias")
    fun findAllFilesByAliasAsFlow(alias: String): Flow<List<FileEntity>>

    @Query("SELECT * FROM files WHERE alias = :alias")
    suspend fun findAllFilesByAlias(alias: String): List<FileEntity>

    @Query("DELETE FROM files WHERE alias = :alias")
    suspend fun deleteAllFilesByAlias(alias: String)

    @Query("UPDATE files SET isSelected = CASE WHEN teseraId = :teseraId THEN 1 ELSE 0 END")
    suspend fun selectFile(teseraId: Int)
    @Update
    suspend fun update(file: FileEntity)
}