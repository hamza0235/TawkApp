package com.example.tawkapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.tawkapp.model.users.Users

@Dao
interface UsersDao {
    @Insert
    suspend fun addUsers(list: List<Users>)

    @Update
    suspend fun updateUser(user: Users)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<Users>
}