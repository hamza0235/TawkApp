package com.example.tawkapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tawkapp.model.users.Users
import com.example.tawkapp.networking.Resource
import com.example.tawkapp.repositories.FakeUserRepository
import com.example.tawkapp.ui.users.UsersViewModel
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserViewModelTest {
    private lateinit var fakeUserRepository: FakeUserRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        fakeUserRepository = FakeUserRepository()

    }

    @Test
    fun inserUsers(){
        val exampleUser = Users("test_user", 123, "asda", "https://api.github.com/users/mojombo","", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","User","", false)
        var listUsers = ArrayList<Users>()
        listUsers.add(exampleUser)
        val value = fakeUserRepository.insertUsers(listUsers)
        Truth.assertThat(value.status).isEqualTo(Resource.Status.SUCCESS)
    }

    @Test
    fun getUsers(){
        val exampleUser = Users("test_user", 123, "asda", "https://api.github.com/users/mojombo","", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","User","", false)
        var listUsers = ArrayList<Users>()
        listUsers.add(exampleUser)
        val value = fakeUserRepository.getUsersList()
        Truth.assertThat(value.status).isEqualTo(Resource.Status.SUCCESS)
    }
}