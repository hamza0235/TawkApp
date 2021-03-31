package com.example.tawkapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tawkapp.model.users.UserDetail
import com.example.tawkapp.model.users.Users

/**
 * SQLite Database for storing the tours.
 */
@Database(
    entities = [Users::class, UserDetail::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UsersDao

    abstract fun userDetailDao(): UserDetailDao
}