package com.example.task1_login_kotlin.di

import com.example.task1_login_kotlin.di.BaseUrl.BASE_URL
import com.example.task1_login_kotlin.api.API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {
    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): API =
        retrofit.create(API::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().callTimeout(30, TimeUnit.SECONDS).build())
        .build()
}


