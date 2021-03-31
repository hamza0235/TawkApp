package com.example.tawkapp.repositories

import com.example.tawkapp.model.users.Users

interface UserRepositoryInterface {

    suspend fun addUser(userList: ArrayList<Users>)
}