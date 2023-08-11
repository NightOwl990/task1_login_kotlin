package com.example.task1_login_kotlin.view.interfaces

import android.text.TextWatcher

interface TextChangeAdapter : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
}