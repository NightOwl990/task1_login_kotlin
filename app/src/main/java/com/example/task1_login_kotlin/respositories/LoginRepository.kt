package com.example.task1_login_kotlin.respositories

import com.example.task1_login_kotlin.api.API
import com.example.task1_login_kotlin.model.Account
import com.example.task1_login_kotlin.utils.CommonUtils
import javax.inject.Inject

class LoginRepository @Inject constructor(private val api: API) {
    suspend fun startLogin(username: String, password: String): String {
        val tokenResponse = api.getTokenSession()
        if (!tokenResponse.isSuccessful) {
            return tokenResponse.code().toString()
        }

        val token = tokenResponse.body()?.request_token
        val loginResponse = api.postUser(Account(username, password, token.toString()))
        if (!loginResponse.isSuccessful) {
            return loginResponse.code().toString()
        }

        val sessionIdResponse = api.getSessionId(Account(username, password, token.toString()))
        if (!sessionIdResponse.isSuccessful) {
            return sessionIdResponse.code().toString()
        }
        sessionIdResponse.body()?.let { CommonUtils.instance.savePref("authToken", it.session_id) }
        return sessionIdResponse.code().toString()
    }
}
