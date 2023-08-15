package com.example.task1_login_kotlin.database.dao

import androidx.room.*
import com.example.task1_login_kotlin.database.entities.Contact

@Dao
interface ContactDAO {
    @Query("SELECT * FROM Contact")
    fun getAllContact(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("DELETE FROM Contact WHERE id = :id")
    fun deleteContactById(id: Int)

    @Query("DELETE FROM Contact")
    fun deleteAllContact()
}