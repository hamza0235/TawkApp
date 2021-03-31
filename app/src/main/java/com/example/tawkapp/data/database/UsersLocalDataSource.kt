package com.example.tawkapp.data.database

import com.example.tawkapp.model.users.Users
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersLocalDataSource @Inject constructor(private val usersDao: UsersDao) {

    suspend fun addUsers(list: List<Users>) = usersDao.addUsers(list)

    suspend fun getUsers() = usersDao.getAllUsers()

    suspend fun updateUser(user: Users) = usersDao.updateUser(user)
}