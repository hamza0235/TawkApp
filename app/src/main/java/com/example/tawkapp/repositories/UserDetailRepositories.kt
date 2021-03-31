package com.example.tawkapp.repositories

import com.example.tawkapp.data.database.UsersDetailLocalDataSource
import com.example.tawkapp.data.database.UsersLocalDataSource
import com.example.tawkapp.model.users.UserDetail
import com.example.tawkapp.model.users.Users
import com.example.tawkapp.utils.Utility
import com.hl.mytourpics.data.remote.ApiRemoteDataSource
import com.example.tawkapp.networking.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDetailRepositories @Inject constructor(
    private val remoteDataSource: ApiRemoteDataSource,
    private val usersDetailLocalDataSource: UsersDetailLocalDataSource,
    private val usersLocalDataSource: UsersLocalDataSource
) {
    suspend fun getUserDetails(userID: Int, userName: String): Resource<UserDetail> {
        var response: Resource<UserDetail>?
        if (usersDetailLocalDataSource.getUserDetail(userID) == null) {
            response = fetchFromServer(userName)
        } else {
            var userDetail = usersDetailLocalDataSource.getUserDetail(userID) as UserDetail
            response = Resource.success(userDetail)
        }


        /*response = if (usersDetailLocalDataSource.getUserDetail(userID) == null) {
            val remoteResponse = remoteDataSource.getUserDetail(userName)
            if (remoteResponse.status == Resource.Status.SUCCESS) {
                usersDetailLocalDataSource.insertUserDetail(remoteResponse.data as UserDetail)
                Resource.success(remoteResponse)
            } else {
                Resource.error(
                    data = null,
                    message = remoteResponse.message ?: Utility.otherErr
                )
            }
        } else {
            Resource.success(userDetail)
        }*/

        return response!!;
    }

    suspend fun fetchFromServer(userName: String): Resource<UserDetail> {
        val remoteResponse = remoteDataSource.getUserDetail(userName)
        if (remoteResponse.status == Resource.Status.SUCCESS) {
            usersDetailLocalDataSource.insertUserDetail(remoteResponse.data as UserDetail)
            Resource.success(remoteResponse)
        } else {
            Resource.error(
                data = null,
                message = remoteResponse.message ?: Utility.otherErr
            )
        }
        return remoteResponse
    }

    suspend fun saveUserNotes(user: Users){
        usersLocalDataSource.updateUser(user)
    }
}