package com.example.avatar_ai_cloud_storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.avatar_ai_cloud_storage.database.entity.PrimaryFeature

/**
 * Data Access Object (DAO) for the "primary_feature" table in the Room database.
 * Contains methods for interacting with the "primary_feature" table.
 */

@Dao
interface PrimaryFeatureDao {
    // Retrieve the primary feature of an anchor.
    @Query("SELECT * FROM primary_feature WHERE anchor LIKE :anchorID")
    suspend fun getPrimaryFeature(anchorID: String): PrimaryFeature?

    // Insert a new primary feature, replaces the original on a conflict.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(primaryFeature: PrimaryFeature)

    // Delete a primary feature based on the feature's name.
    @Query("DELETE FROM primary_feature WHERE feature LIKE :featureName")
    suspend fun delete(featureName: String)
}