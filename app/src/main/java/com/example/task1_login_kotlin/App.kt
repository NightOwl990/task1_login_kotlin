package com.example.task1_login_kotlin

import android.app.Application
import androidx.room.Room
import com.example.task1_login_kotlin.database.ContactDB
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    private lateinit var db: ContactDB
    fun getDb(): ContactDB {
        return db
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initDB()
    }

    private fun initDB() {
        db = Room.databaseBuilder(applicationContext, ContactDB::class.java, "contact_db").build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}