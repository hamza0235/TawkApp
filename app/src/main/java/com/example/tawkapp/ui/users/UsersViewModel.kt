package com.example.tawkapp.ui.users

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.tawkapp.repositories.UsersRepositories
import com.example.tawkapp.model.users.Users
import com.example.tawkapp.utils.Utility
import com.example.tawkapp.networking.Resource
import kotlinx.coroutines.launch

class UsersViewModel @ViewModelInject constructor(
    private val repository: UsersRepositories) : ViewModel() {

    var userResponse: MutableLiveData<Resource<List<Users>>> = MutableLiveData<Resource<List<Users>>>()

    fun getUsers() : LiveData<Resource<List<Users>>>{
        if (userResponse.value == null || userResponse.value!!.data == null){
            getUserList(0)
        }

        return userResponse
    }

    fun getUserList(userID: Int) {
        viewModelScope.launch {
            try {
                userResponse.value = repository.getUsers(userID)
            } catch (ex: Exception) {
                userResponse.value =
                    Resource.error(data = null, message = ex.message ?: Utility.otherErr)
            }
        }
    }

}