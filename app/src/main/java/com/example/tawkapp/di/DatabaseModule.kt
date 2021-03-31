package com.example.tawkapp.di

import android.content.Context
import androidx.room.Room
import com.example.tawkapp.data.database.AppDatabase
import com.example.tawkapp.data.database.UserDetailDao
import com.example.tawkapp.data.database.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext applicationContext: Context): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppDatabase::class.java, "github_users_db")
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersDao(database: AppDatabase): UsersDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideUserDetailDao(database: AppDatabase): UserDetailDao {
        return database.userDetailDao()
    }
}