package com.example.tawkapp.data.database

import com.example.tawkapp.model.users.UserDetail
import com.example.tawkapp.model.users.Users
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersDetailLocalDataSource @Inject constructor(private val userDetailDao: UserDetailDao) {

    suspend fun getUserDetail(id: Int) = userDetailDao.getUserDetailWithId(id)

    suspend fun insertUserDetail(userDetail: UserDetail) = userDetailDao.insertUserDetail(userDetail)
}