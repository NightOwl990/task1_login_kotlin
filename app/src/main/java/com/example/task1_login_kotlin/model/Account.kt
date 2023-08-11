package com.example.task1_login_kotlin.model

import androidx.annotation.Keep

@Keep
data class Account(
    val username: String,
    val password: String,
    val request_token: String,
)