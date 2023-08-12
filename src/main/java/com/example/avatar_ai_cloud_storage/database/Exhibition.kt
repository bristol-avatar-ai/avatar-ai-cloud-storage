package com.example.avatar_ai_cloud_storage.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * This data class represents an [Exhibition] entity in the Room database.
 * An "exhibition" represents any point of interest in the mapped area.
 * [name]: Exhibition name.
 * [anchor]: Parent Anchor ID. (There can be multiple exhibitions at an anchor.)
 * [description]: Exhibition description (read to the user).
 */

@Entity(
    tableName = "exhibition",
    foreignKeys = [
        // An Exhibition is deleted if their parent Anchor ID is deleted or modified.
        ForeignKey(
            entity = Anchor::class,
            parentColumns = ["id"],
            childColumns = ["anchor"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Exhibition(
    @PrimaryKey
    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "anchor")
    val anchor: String,

    @ColumnInfo(name = "description")
    val description: String
)