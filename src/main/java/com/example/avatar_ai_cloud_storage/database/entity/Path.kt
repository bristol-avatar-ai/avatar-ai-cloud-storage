package com.example.avatar_ai_cloud_storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 * This data class represents an [Path] entity in the Room database.
 * An "path" represents a straight-line path from one Cloud Anchor to another.
 * [origin]: Origin Anchor ID.
 * [destination]: Destination Anchor ID.
 * [distance]: Relative cost/weight of path.
 */

@Entity(
    tableName = "path",
    primaryKeys = ["origin", "destination"],
    foreignKeys = [
        // An Path is deleted if either parent Anchor ID is deleted or modified.
        ForeignKey(
            entity = Anchor::class,
            parentColumns = ["id"],
            childColumns = ["origin"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Anchor::class,
            parentColumns = ["id"],
            childColumns = ["destination"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Path(
    @ColumnInfo(name = "origin")
    val origin: String,

    @ColumnInfo(name = "destination")
    val destination: String,

    @ColumnInfo(name = "distance")
    val distance: Int
)