package com.example.avatar_ai_cloud_storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PathDao {
    @Query("SELECT * FROM path")
    fun getPaths(): List<Path>

    @Query("SELECT * FROM path WHERE origin LIKE :anchorId")
    fun getPathsFromAnchor(anchorId: String): Flow<List<Path>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(path: Path)

    @Query("UPDATE path SET distance = :distance WHERE origin LIKE :origin AND destination LIKE :destination")
    suspend fun update(origin: String, destination: String, distance: Int)

    @Query("DELETE FROM path WHERE origin LIKE :origin AND destination LIKE :destination")
    suspend fun delete(origin: String, destination: String)
}