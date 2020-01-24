package com.example.h_mal.superawesomefilereader

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.h_mal.superawesomefilereader.db.AnagramMapDao
import com.example.h_mal.superawesomefilereader.db.AppDatabase
import com.example.h_mal.superawesomefilereader.db.entities.AnagramMap
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private lateinit var anagramMapDao: AnagramMapDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = AppDatabase.invoke(context)
        anagramMapDao = db.getMapDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.clearAllTables()
    }

    @Test
    @Throws(Exception::class)
    fun addAndUpdateEntries() {

        val anagramPair = AnagramMap("abc", "bca")
        val long = runBlocking { anagramMapDao.saveAnagramPair(anagramPair) }
        assertEquals(long, (1).toLong())

        val anagramPair2 = AnagramMap("abc", "cab")
        val long2 = runBlocking { anagramMapDao.saveAnagramPair(anagramPair2) }
        assertEquals(long2, (-1).toLong())
    }
}
