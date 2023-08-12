package com.example.avatar_ai_cloud_storage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This data class represents an [Anchor] entity in the Room database.
 * The "anchor" table is a list of all Cloud Anchor IDs in use.¬
 * [id]: Cloud Anchor reference ID.
 * [description]: Description used only for anchor management.
 */

@Entity(tableName = "anchor")
data class Anchor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "description")
    val description: String
)