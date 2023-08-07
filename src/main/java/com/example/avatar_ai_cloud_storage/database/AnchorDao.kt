package com.example.avatar_ai_cloud_storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnchorDao {
    @Query("SELECT * FROM anchor")
    fun getAnchors(): List<Anchor>

    @Query("SELECT * FROM anchor")
    fun getAnchorsFlow(): Flow<List<Anchor>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(anchor: Anchor)

    @Query("UPDATE anchor SET description = :description WHERE id LIKE :anchorId")
    suspend fun update(anchorId: String, description: String)

    @Query("DELETE FROM anchor WHERE id LIKE :anchorId")
    suspend fun delete(anchorId: String)
}