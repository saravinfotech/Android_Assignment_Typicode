package com.assignment

import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.assignment.ui.adapters.AlbumsListAdapter
import com.assignment.util.MockResponseFileReader
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EspressoUITests : BaseTestClass(){

    @Test
    fun appContextGivesCorrectPackageName() {
        val appContext: Context = getApplicationContext()
        Assert.assertEquals("com.assignment", appContext.packageName)
    }

    @Test
    fun recViewAlbumElement1TitleCheck() {
        runBlocking {
            server.apply {
                enqueue(MockResponse().setBody(MockResponseFileReader("albums_response.json").content))
            }
            val response = placeholderApi.getAlbums()
            if (response.isSuccessful) {
                response.body()?.let { models ->
                    val sortedModels = models.sortedBy { it.title }
                    val modelAt1Title = sortedModels[1].title

                    onView(withId(R.id.recView))
                            .check(matches(atPosition(1, hasDescendant(withText(modelAt1Title)))))
                }
            }
        }
    }

    @Test
    fun detailViewAlbumElement5UserEmailCheck() {

        runBlocking {
            server.apply {
                enqueue(MockResponse().setBody(MockResponseFileReader("user_response_9.json").content))
            }
            val response = placeholderApi.getUser(9)
            if (response.isSuccessful) {
                response.body()?.let { model ->

                    val email = model.email

                    onView(withId(R.id.recView))
                            .perform(actionOnItemAtPosition<AlbumsListAdapter.ViewClass>(5, click()))

                    Espresso.pressBack()

                    onView(withId(R.id.recView))
                            .perform(actionOnItemAtPosition<AlbumsListAdapter.ViewClass>(5, click()))

                    onView(withId(R.id.userEmail))
                            .check(matches(withText(email)))

                }
            }
        }
    }

    @Test
    fun detailViewAlbumElement2UsernameCheck() {

        runBlocking {
            server.apply {
                enqueue(MockResponse().setBody(MockResponseFileReader("user_response_4.json").content))
            }
            val response = placeholderApi.getUser(4)
            if (response.isSuccessful) {
                response.body()?.let { model ->
                    val username = model.name

                    onView(withId(R.id.recView))
                            .perform(actionOnItemAtPosition<AlbumsListAdapter.ViewClass>(2, click()))
                    Espresso.pressBack()
                    onView(withId(R.id.recView))
                            .perform(actionOnItemAtPosition<AlbumsListAdapter.ViewClass>(2, click()))

                    onView(withId(R.id.username))
                            .check(matches(withText(username)))

                }
            }
        }
    }

    @Test
    fun detailViewAlbumElement2UserEmailCheck() {

        runBlocking {
            server.apply {
                enqueue(MockResponse().setBody(MockResponseFileReader("user_response_4.json").content))
            }
            val response = placeholderApi.getUser(4)
            if (response.isSuccessful) {
                response.body()?.let { model ->
                    val email = model.email

                    onView(withId(R.id.recView))
                            .perform(actionOnItemAtPosition<AlbumsListAdapter.ViewClass>(2, click()))
                    Espresso.pressBack()
                    onView(withId(R.id.recView))
                            .perform(actionOnItemAtPosition<AlbumsListAdapter.ViewClass>(2, click()))

                    onView(withId(R.id.userEmail))
                            .check(matches(withText(email)))

                }
            }
        }
    }

    @Test
    fun recViewAlbumElement7TitleCheck() {
        runBlocking {
            server.apply {
                enqueue(MockResponse().setBody(MockResponseFileReader("albums_response.json").content))
            }
            val response = placeholderApi.getAlbums()
            if (response.isSuccessful) {
                response.body()?.let { models ->
                    val sortedModels = models.sortedBy { it.title }
                    val title = sortedModels[7].title
                    onView(withId(R.id.recView))
                            .check(
                                    matches(
                                            atPosition(
                                                    7,
                                                    hasDescendant(withText(title))
                                            )
                                    )
                            )
                }
            }
        }
    }
}
