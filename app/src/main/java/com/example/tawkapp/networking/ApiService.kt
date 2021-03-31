package com.hl.mytourpics.networking

import com.example.tawkapp.model.users.UserDetail
import com.example.tawkapp.model.users.Users
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET(RestConfig.USERS_ENDPOINT)
    suspend fun getUsers(@Query("since") userID: Int): Response<List<Users>>

    @GET("users/{userName}")
    suspend fun getUserDetail(@Path("userName") userName: String): Response<UserDetail>
}