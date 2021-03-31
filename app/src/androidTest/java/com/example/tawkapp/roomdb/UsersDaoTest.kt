package com.example.tawkapp.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.tawkapp.data.database.AppDatabase
import com.example.tawkapp.data.database.UsersDao
import com.example.tawkapp.model.users.Users
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class UsersDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: UsersDao

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDataBase")
    lateinit var database: AppDatabase

    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.userDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertUserTesting() = runBlockingTest {
        val exampleUser = Users("test_user", 123, "asda", "https://api.github.com/users/mojombo","", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","User","", false)
        var listUsers = ArrayList<Users>()
        listUsers.add(exampleUser)
        dao.addUsers(listUsers)

        val list = dao.getAllUsers() as ArrayList
        assertThat(exampleUser.id).isEqualTo(list.get(0).id)

    }

}