package com.example.task1_login_kotlin.model

import androidx.annotation.Keep

@Keep
data class ReqToken(
    val success: Boolean,
    val expires_at: String,
    val request_token: String,
)