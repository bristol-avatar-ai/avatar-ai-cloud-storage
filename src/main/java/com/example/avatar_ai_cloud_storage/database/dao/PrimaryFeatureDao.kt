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
    fun getPrimaryFeature(anchorID: String): PrimaryFeature?

    // Insert a new primary feature, aborts on a conflict.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(primaryFeature: PrimaryFeature)

    // Update an anchor's primary feature based on its ID.
    @Query("UPDATE primary_feature SET feature = :featureName WHERE anchor LIKE :anchorId")
    suspend fun update(anchorId: String, featureName: String)

    // Delete a primary feature based on its Anchor ID.
    @Query("DELETE FROM primary_feature WHERE anchor LIKE :anchorID")
    suspend fun delete(anchorID: String)
}