package com.example.avatar_ai_cloud_storage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExhibitionDao {
    @Query("SELECT * FROM exhibition")
    fun getExhibitions(): List<Exhibition>

    @Query("SELECT * FROM exhibition WHERE anchor LIKE :anchorId")
    fun getExhibitionsAtAnchor(anchorId: String): Flow<List<Exhibition>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(exhibition: Exhibition)

    @Query("UPDATE exhibition SET description = :description WHERE name LIKE :name")
    suspend fun update(name: String, description: String)

    @Query("DELETE FROM exhibition WHERE name LIKE :name")
    suspend fun delete(name: String)
}