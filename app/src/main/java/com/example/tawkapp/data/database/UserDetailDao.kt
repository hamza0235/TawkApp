package com.example.tawkapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tawkapp.model.users.UserDetail
import com.example.tawkapp.model.users.Users

@Dao
interface UserDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserDetail(userDetail: UserDetail)

    @Query("SELECT * FROM user_detail_table WHERE id= :id")
    suspend fun getUserDetailWithId(id: Int): UserDetail?
}