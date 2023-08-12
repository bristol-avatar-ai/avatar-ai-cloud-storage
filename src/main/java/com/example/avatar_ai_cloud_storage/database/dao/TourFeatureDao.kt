package com.example.avatar_ai_cloud_storage.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.avatar_ai_cloud_storage.database.entity.TourFeature
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the "tour_feature" table in the Room database.
 * Contains methods for interacting with the "tour_feature" table.
 */

@Dao
interface TourFeatureDao {
    // Retrieve an ordered list of feature names in the tour.
    @Query("SELECT feature FROM tour_feature ORDER BY number ASC")
    fun getTourFeatures(): List<String>

    // Retrieve an ordered Flow of feature name in the tour (allows asynchronous data observation).
    @Query("SELECT feature FROM tour_feature ORDER BY number ASC")
    fun getTourFeaturesFlow(): Flow<List<String>>

    // Insert a new tour feature, aborts on a conflict.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(tourFeature: TourFeature)

    // Update a tour feature based on its number.
    @Query("UPDATE tour_feature SET feature = :featureName WHERE number LIKE :tourNumber")
    suspend fun update(tourNumber: Int, featureName: String)

    // Delete a tour feature based on its number.
    @Query("DELETE FROM tour_feature WHERE number LIKE :tourNumber")
    suspend fun delete(tourNumber: Int)
}