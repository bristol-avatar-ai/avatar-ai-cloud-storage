package com.example.avatar_ai_cloud_storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the "exhibition" table in the Room database.
 * Contains methods for interacting with the "exhibition" table.
 */

@Dao
interface ExhibitionDao {
    // Retrieve a list of all exhibitions.
    @Query("SELECT * FROM exhibition")
    fun getExhibitions(): List<Exhibition>

    // Retrieve a specific exhibition by its name.
    @Query("SELECT * FROM exhibition WHERE name LIKE :name")
    fun getExhibition(name: String): Exhibition?

    // Retrieve exhibitions located at a specific anchor, returned as a Flow.
    @Query("SELECT * FROM exhibition WHERE anchor LIKE :anchorId")
    fun getExhibitionsAtAnchor(anchorId: String): Flow<List<Exhibition>>

    // Insert a new exhibition, aborts on a conflict.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(exhibition: Exhibition)

    // Update an exhibition's description based on its name.
    @Query("UPDATE exhibition SET description = :description WHERE name LIKE :name")
    suspend fun update(name: String, description: String)

    // Delete an exhibition based on its name.
    @Query("DELETE FROM exhibition WHERE name LIKE :name")
    suspend fun delete(name: String)
}