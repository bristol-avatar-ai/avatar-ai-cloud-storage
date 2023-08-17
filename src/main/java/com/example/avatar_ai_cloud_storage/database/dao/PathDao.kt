package com.example.avatar_ai_cloud_storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.avatar_ai_cloud_storage.database.entity.Path
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the "path" table in the Room database.
 * Contains methods for interacting with the "path" table.
 */

@Dao
interface PathDao {
    // Retrieve a list of all paths.
    @Query("SELECT * FROM path")
    suspend fun getPaths(): List<Path>

    // Retrieve paths originating from an anchor, returned as a Flow.
    @Query("SELECT * FROM path WHERE anchor1 LIKE :anchorId OR anchor2 LIKE :anchorId")
    fun getPathsFromAnchor(anchorId: String): Flow<List<Path>>

    // Retrieve the number of paths originating from an anchor.
    @Query("SELECT COUNT(anchor1) FROM path WHERE anchor1 LIKE :anchorId OR anchor2 LIKE :anchorId")
    suspend fun countPathsFromAnchor(anchorId: String): Int

    // Insert a new path, aborts on a conflict.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(path: Path)

    // Update a path's distance based on its anchor1 and anchor2.
    @Query("UPDATE path SET distance = :distance WHERE anchor1 LIKE :anchor1 AND anchor2 LIKE :anchor2")
    suspend fun update(anchor1: String, anchor2: String, distance: Int)

    // Delete a path based on its anchor1 and anchor2.
    @Query("DELETE FROM path WHERE anchor1 LIKE :anchor1 AND anchor2 LIKE :anchor2")
    suspend fun delete(anchor1: String, anchor2: String)
}