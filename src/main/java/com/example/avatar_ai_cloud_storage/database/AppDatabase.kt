package com.example.avatar_ai_cloud_storage.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.avatar_ai_cloud_storage.database.AppDatabase.Companion.getDatabase
import com.example.avatar_ai_cloud_storage.database.dao.AnchorDao
import com.example.avatar_ai_cloud_storage.database.dao.FeatureDao
import com.example.avatar_ai_cloud_storage.database.dao.PathDao
import com.example.avatar_ai_cloud_storage.database.dao.PrimaryFeatureDao
import com.example.avatar_ai_cloud_storage.database.dao.TourFeatureDao
import com.example.avatar_ai_cloud_storage.database.entity.Anchor
import com.example.avatar_ai_cloud_storage.database.entity.Feature
import com.example.avatar_ai_cloud_storage.database.entity.Path
import com.example.avatar_ai_cloud_storage.database.entity.PrimaryFeature
import com.example.avatar_ai_cloud_storage.database.entity.TourFeature
import com.example.avatar_ai_cloud_storage.network.CloudStorageApi
import java.io.File

private const val TAG = "AppDatabase"

/**
 * Room database class that defines the database schema and provides access to DAOs.
 * The [getDatabase] function gets an instance of the database, downloading the file
 * and creating the instance if necessary.
 */

@Database(
    version = 8,
    entities = [Anchor::class, Path::class, Feature::class, PrimaryFeature::class, TourFeature::class]
)
abstract class AppDatabase : RoomDatabase() {
    // These functions provide easy access to the DAOs.
    abstract fun anchorDao(): AnchorDao
    abstract fun pathDao(): PathDao
    abstract fun featureDao(): FeatureDao
    abstract fun primaryFeatureDao(): PrimaryFeatureDao
    abstract fun tourFeatureDao(): TourFeatureDao

    companion object {
        // Database file name (keep public).
        const val FILENAME = "data"

        /*
        * Volatile instance ensures proper synchronization of database access.
        * Only one instance of the database can exists at any time.
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /*
        * Get an instance of the database, creating it if necessary.
        * Downloads and updates the database file if needed.
         */
        suspend fun getDatabase(context: Context): AppDatabase? {
            val databaseFile = File(context.filesDir, FILENAME)

            // Return the database instance if it exits, create it if it does not.
            if (INSTANCE != null) {
                Log.i(TAG, "Database instance exists")
            }
            return INSTANCE ?: if (
                CloudStorageApi.updateDatabase(databaseFile) || databaseFile.exists()
            ) {
                try {
                    createDatabase(context, databaseFile)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to create database", e)
                    null
                }
            } else {
                Log.e(TAG, "Unable to download database")
                null
            }
        }

        /*
        * Create a new database instance using Room's database builder.
        * Only one thread of execution at a time can enter the synchronized block.
        * This ensures that the database is only initialised once.
         */
        private fun createDatabase(context: Context, databaseFile: File): AppDatabase {
            return synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    databaseFile.toPath().toString()
                )
                    // Destructively recreate tables if no Migration found for new schema.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                Log.i(TAG, "Database instance created")
                instance
            }
        }

        /*
        * Close the database instance.
         */
        fun close() {
            INSTANCE?.close()
            INSTANCE = null
            Log.i(TAG, "Database instance closed")
        }

    }
}