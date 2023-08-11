package com.example.task1_login_kotlin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task1_login_kotlin.database.dao.ContactDAO
import com.example.task1_login_kotlin.database.entities.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDB : RoomDatabase(){
    abstract fun contactDao(): ContactDAO
}