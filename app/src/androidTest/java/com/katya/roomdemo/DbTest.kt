package com.katya.roomdemo

import com.katya.roomdemo.db.SubscriberDAO
import com.katya.roomdemo.db.SubscriberDatabase

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.katya.roomdemo.db.Subscriber
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class DbTest {

    private lateinit var subDao: SubscriberDAO
    private lateinit var db: SubscriberDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SubscriberDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        subDao = db.subscriberDAO
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    suspend fun testDBTest() {
        val sub = Subscriber(1, "katya", "kkkk@gmail.com")
        subDao.insertSubscriber(sub)
        val all = subDao.getAllSubscribers()
        assertNotNull(all)
    }
}