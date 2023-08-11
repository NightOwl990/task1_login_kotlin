package com.example.task1_login_kotlin.api

import com.example.task1_login_kotlin.model.Account
import com.example.task1_login_kotlin.model.ReqToken
import com.example.task1_login_kotlin.model.Session
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface API {
    companion object {
        const val API_KEY: String = "d68581f64c502852c33b6a5a88d1a589"
    }

    @GET("authentication/token/new?api_key=$API_KEY")
    suspend fun getTokenSession(): Response<ReqToken>

    @POST("authentication/token/validate_with_login?api_key=$API_KEY")
    suspend fun postUser(@Body account: Account?): Response<ReqToken>

    @POST("authentication/session/new?api_key=$API_KEY")
    suspend fun getSessionId(@Body account: Account?): Response<Session>
}