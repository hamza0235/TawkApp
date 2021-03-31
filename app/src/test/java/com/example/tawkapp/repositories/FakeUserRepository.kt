package com.example.tawkapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.tawkapp.data.database.UsersLocalDataSource
import com.example.tawkapp.model.users.Users
import com.example.tawkapp.networking.Resource
import com.hl.mytourpics.data.remote.ApiRemoteDataSource
import javax.inject.Inject

class FakeUserRepository {
    private var users = mutableListOf<Users>()
    private val usersLiveData = MutableLiveData<List<Users>>(users)


    fun getUsersList() :Resource<List<Users>>{
        return Resource.success(users)
    }

    fun insertUsers(usersList: List<Users>) :Resource<List<Users>>{
        users.addAll(usersList)
        return Resource.success(listOf())
    }



}