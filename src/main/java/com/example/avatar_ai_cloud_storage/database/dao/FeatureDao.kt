package com.example.avatar_ai_cloud_storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.avatar_ai_cloud_storage.database.entity.Feature
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the "feature" table in the Room database.
 * Contains methods for interacting with the "feature" table.
 */

@Dao
interface FeatureDao {
    // Retrieve a list of all features.
    @Query("SELECT * FROM feature")
    suspend fun getFeatures(): List<Feature>

    // Retrieve an ordered Flow of all features (allows asynchronous data observation).
    @Query("SELECT * FROM feature ORDER BY name ASC")
    fun getFeaturesFlow(): Flow<List<Feature>>

    // Retrieve a specific feature by its name.
    @Query("SELECT * FROM feature WHERE name LIKE :name")
    suspend fun getFeature(name: String): Feature?

    // Retrieve features located at a specific anchor, returned as a Flow.
    @Query("SELECT * FROM feature WHERE anchor LIKE :anchorId")
    fun getFeaturesAtAnchor(anchorId: String): Flow<List<Feature>>

    // Insert a new feature, aborts on a conflict.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(feature: Feature)

    // Update a feature's description based on its name.
    @Query("UPDATE feature SET anchor = :anchorId, description = :description WHERE name LIKE :name")
    suspend fun update(name: String, anchorId: String, description: String)

    // Delete a feature based on its name.
    @Query("DELETE FROM feature WHERE name LIKE :name")
    suspend fun delete(name: String)
}