package com.example.tawkapp.repositories

import com.example.tawkapp.data.database.UsersLocalDataSource
import com.example.tawkapp.model.users.Users
import com.example.tawkapp.utils.Utility
import com.hl.mytourpics.data.remote.ApiRemoteDataSource
import com.example.tawkapp.networking.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class UsersRepositories @Inject constructor(
    private val remoteDataSource: ApiRemoteDataSource,
    private val usersLocalDataSource: UsersLocalDataSource
) : UserRepositoryInterface {
    suspend fun getUsers(userID: Int): Resource<List<Users>> {
        if (userID == 0){
            return if (usersLocalDataSource.getUsers().isEmpty()){
                fetchFromServer(userID)
            }else{
                Resource.success(usersLocalDataSource.getUsers())
            }
        }
        return fetchFromServer(userID)
    }

    suspend fun fetchFromServer(userID: Int) : Resource<List<Users>> {
        val remoteResponse = remoteDataSource.getUsers(userID)
        if (remoteResponse.status == Resource.Status.SUCCESS) {
            addUser(remoteResponse.data as ArrayList<Users>)
            Resource.success(remoteResponse)
        } else {
            Resource.error(
                data = null,
                message = remoteResponse.message ?: Utility.otherErr
            )
        }
        return remoteResponse
    }

    override suspend fun addUser(userList: ArrayList<Users>) {
        usersLocalDataSource.addUsers(userList)
    }
}