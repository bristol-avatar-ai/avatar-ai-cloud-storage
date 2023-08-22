package com.example.avatar_ai_cloud_storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.avatar_ai_cloud_storage.database.entity.Anchor
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the "anchor" table in the Room database.
 * Contains methods for interacting with the "anchor" table.
 */

@Dao
interface AnchorDao {
    // Retrieve a list of all anchors.
    @Query("SELECT * FROM anchor")
    suspend fun getAnchors(): List<Anchor>

    // Retrieve an ordered Flow of all anchors (allows asynchronous data observation).
    @Query("SELECT * FROM anchor ORDER BY name ASC")
    fun getAnchorsFlow(): Flow<List<Anchor>>

    // Retrieve a specific anchor by its ID.
    @Query("SELECT * FROM anchor WHERE id LIKE :anchorId")
    suspend fun getAnchor(anchorId: String): Anchor?

    // Insert a new anchor, aborts on a conflict.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(anchor: Anchor)

    // Insert a new anchor, ignore on a conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIfNotPresent(anchor: Anchor)

    // Update an anchor's name based on its ID.
    @Query("UPDATE anchor SET name = :name WHERE id LIKE :anchorId")
    suspend fun update(anchorId: String, name: String)

    // Update an anchor's days to expiration based on its ID.
    @Query("UPDATE anchor SET days_to_expiration = :daysToExpiration WHERE id LIKE :anchorId")
    suspend fun updateExpiration(anchorId: String, daysToExpiration: String)

    // Delete an anchor based on its ID.
    @Query("DELETE FROM anchor WHERE id LIKE :anchorId")
    suspend fun delete(anchorId: String)
}