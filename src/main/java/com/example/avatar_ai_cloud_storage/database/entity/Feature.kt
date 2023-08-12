package com.example.avatar_ai_cloud_storage.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * This data class represents an [Feature] entity in the Room database.
 * An "feature" represents any point of interest in the mapped area.
 * [name]: Feature name.
 * [anchor]: Parent Anchor ID. (There can be multiple features at an anchor.)
 * [description]: Feature description (read to the user).
 */

@Entity(
    tableName = "feature",
    foreignKeys = [
        // A Feature is deleted if its parent Anchor ID is deleted or modified.
        ForeignKey(
            entity = Anchor::class,
            parentColumns = ["id"],
            childColumns = ["anchor"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Feature(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "anchor")
    val anchor: String,

    @ColumnInfo(name = "description")
    val description: String
)