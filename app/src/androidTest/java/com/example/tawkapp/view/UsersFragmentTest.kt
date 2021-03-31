package com.example.tawkapp.view

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.tawkapp.BundleEquals
import com.example.tawkapp.R
import com.example.tawkapp.adapters.UsersAdapter
import com.example.tawkapp.launchFragmentInHiltContainer
import com.example.tawkapp.model.users.Users
import com.example.tawkapp.ui.users.UsersFragment
import com.example.tawkapp.ui.users.UsersFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.mockito.Mockito.verify


@MediumTest
@HiltAndroidTest
class UsersFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromUserToUserDetails(){
        val  navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<UsersFragment>(){
            Navigation.setViewNavController(requireView(), navController)
        }

        val position = 40
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<UsersAdapter.UsersViewHolder>(position, click())
            )

        val exampleUser = Users(
            "test_user",
            123,
            "asda",
            "https://api.github.com/users/mojombo",
            "",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "https://api.github.com/users/mojombo",
            "User",
            "",
            false
        )


        Mockito.verify(navController).navigate(
            UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(
                exampleUser
            )
        )
    }


}