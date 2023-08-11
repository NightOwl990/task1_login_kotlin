package com.example.task1_login_kotlin.database.entities

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Phone Number") val phoneNumber: String,
) {
    constructor(name: String, phoneNumber: String) : this(0, name, phoneNumber)
}