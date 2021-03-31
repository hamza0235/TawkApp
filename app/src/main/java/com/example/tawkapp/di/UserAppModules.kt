package com.example.tawkapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hl.mytourpics.networking.ApiService
import com.hl.mytourpics.networking.RestConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object UserAppModules {
    @Provides
    fun provideBaseUrl() = RestConfig.API_SERVER_BASE_URL

    @Provides
    fun provideGSON(): Gson = GsonBuilder().setLenient().create()


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, baseURL: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseURL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}