package com.example.avatar_ai_cloud_storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * This data class represents a [TourFeature] entity in the Room database.
 * The "tour_feature" table lists the features to be shown on a tour.
 * [number]: Order to show the features in.
 * [feature]: Feature name.
 */

@Entity(
    tableName = "tour_feature",
    foreignKeys = [
        // A TourFeature is deleted if its parent Feature is deleted or modified.
        ForeignKey(
            entity = Feature::class,
            parentColumns = ["name"],
            childColumns = ["feature"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TourFeature(
    @PrimaryKey
    @ColumnInfo(name = "number")
    val number: Int,

    @ColumnInfo(name = "feature")
    val feature: String
)