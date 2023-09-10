package com.example.avatar_ai_cloud_storage

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.avatar_ai_cloud_storage.database.AppDatabase
import com.example.avatar_ai_cloud_storage.database.entity.Anchor
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {
    private lateinit var database: AppDatabase

    @Before
    fun createDatabase() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    }

    @Test
    fun testAnchorInsertion() = runBlocking {
        val anchorId = "JF872JE9F8Y23HRD"
        val anchor = Anchor(anchorId, "Desk", "20")
        database.anchorDao().insert(anchor)
        assertTrue(database.anchorDao().getAnchor(anchorId)?.equals(anchor) ?: false)
    }

    @After
    fun close() {
        database.close()
    }
}