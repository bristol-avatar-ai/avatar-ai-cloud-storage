package com.example.avatar_ai_cloud_storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * This data class represents an [Path] entity in the Room database.
 * An "path" represents a bidirectional path from one Cloud Anchor to another.
 * [anchor1]: First Anchor ID.
 * [anchor2]: Second Anchor ID.
 * [distance]: Relative cost/weight of path.
 */

@Entity(
    tableName = "path",
    primaryKeys = ["anchor1", "anchor2"],
    foreignKeys = [
        // An Path is deleted if either parent Anchor ID is deleted or modified.
        ForeignKey(
            entity = Anchor::class,
            parentColumns = ["id"],
            childColumns = ["anchor1"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Anchor::class,
            parentColumns = ["id"],
            childColumns = ["anchor2"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Path(
    @ColumnInfo(name = "anchor1")
    val anchor1: String,

    @ColumnInfo(name = "anchor2")
    val anchor2: String,

    @ColumnInfo(name = "distance")
    val distance: Int
)