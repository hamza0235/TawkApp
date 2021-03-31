package com.example.tawkapp.ui.user_detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tawkapp.model.users.UserDetail
import com.example.tawkapp.model.users.Users
import com.example.tawkapp.repositories.UserDetailRepositories
import com.example.tawkapp.utils.Utility
import com.example.tawkapp.networking.Resource
import kotlinx.coroutines.launch

class UserDetailViewModel @ViewModelInject constructor(
    private val repositories: UserDetailRepositories
) : ViewModel() {

    var userDetailResponse: MutableLiveData<Resource<UserDetail>> = MutableLiveData<Resource<UserDetail>>()

    fun getUser(userID: Int, userName: String) : LiveData<Resource<UserDetail>>{
        if (userDetailResponse.value == null || userDetailResponse.value!!.data == null){
            getUserDetail(userID, userName)
        }

        return userDetailResponse
    }

    fun getUserDetail(userID: Int, userName: String) {
        viewModelScope.launch {
            try {
                userDetailResponse.value = repositories.getUserDetails(userID, userName)
            } catch (ex: Exception) {
                userDetailResponse.value =
                    Resource.error(data = null, message = ex.message ?: Utility.otherErr)
            }
        }
    }

    suspend fun saveUserNotes(user: Users){
        repositories.saveUserNotes(user)
    }

}