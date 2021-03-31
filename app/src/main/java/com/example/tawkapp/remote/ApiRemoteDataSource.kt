package com.hl.mytourpics.data.remote

import com.hl.mytourpics.networking.ApiService
import com.hl.mytourpics.networking.BaseDataSource
import okhttp3.RequestBody
import javax.inject.Inject

class ApiRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {
    suspend fun getUsers(userID: Int) = getResult {
        apiService.getUsers(userID)
    }

    suspend fun getUserDetail(userName: String) = getResult {
        apiService.getUserDetail(userName)
    }
}