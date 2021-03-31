package com.example.tawkapp.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.work.impl.utils.LiveDataUtils
import com.example.tawkapp.data.database.AppDatabase
import com.example.tawkapp.data.database.UserDetailDao
import com.example.tawkapp.data.database.UsersDao
import com.example.tawkapp.getOrAwaitValue
import com.example.tawkapp.model.users.UserDetail
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
class UsersDetailDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: UserDetailDao

    @Inject
    @Named("testDataBase")
    lateinit var database: AppDatabase

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.userDetailDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertUserDetailTesting() = runBlockingTest {
        val exampleUser = UserDetail(123, "", "asda", "https://api.github.com/users/mojombo","", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo",555,"https://api.github.com/users/mojombo", 444, "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","User","", "Test" ,"node_id", "https://api.github.com/users/mojombo", 12, 12)
        dao.insertUserDetail(exampleUser)

        val userDetail = dao.getUserDetailWithId(exampleUser.id)
        assertThat(userDetail).isNotNull()

    }

    @Test
    fun getUserDetailTesting() = runBlockingTest {
        val exampleUser = UserDetail(123, "", "asda", "https://api.github.com/users/mojombo","", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo",555,"https://api.github.com/users/mojombo", 444, "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo", "https://api.github.com/users/mojombo","https://api.github.com/users/mojombo","User","", "Test" ,"node_id", "https://api.github.com/users/mojombo", 12, 12)
        dao.insertUserDetail(exampleUser)

        val list = dao.getUserDetailWithId(exampleUser.id)
        assertThat(list).isNotNull()

    }

}