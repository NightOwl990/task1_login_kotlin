package com.example.task1_login_kotlin.model

import androidx.annotation.Keep

@Keep
data class Session (var success: Boolean, var session_id: String)