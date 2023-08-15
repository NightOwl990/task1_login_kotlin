package com.example.task1_login_kotlin.view.interfaces

import com.example.task1_login_kotlin.database.entities.Contact

interface OnDeleteContactCallBack {
    fun updateRecyclerUI(list : List<Contact>)
}