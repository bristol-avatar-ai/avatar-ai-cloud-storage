package com.example.avatar_ai_cloud_storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * This data class represents a [PrimaryFeature] entity in the Room database.
 * A "primary_feature" represents the primary/dominant feature located at an anchor.
 * [anchor]: Parent Anchor ID.
 * [feature]: Feature name.
 */

@Entity(
    tableName = "primary_feature",
    foreignKeys = [
        // A PrimaryFeature is deleted if its parents are deleted or modified.
        ForeignKey(
            entity = Anchor::class,
            parentColumns = ["id"],
            childColumns = ["anchor"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Feature::class,
            parentColumns = ["name"],
            childColumns = ["feature"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class PrimaryFeature(
    @PrimaryKey
    @ColumnInfo(name = "anchor")
    val anchor: String,

    @ColumnInfo(name = "feature")
    val feature: String
)