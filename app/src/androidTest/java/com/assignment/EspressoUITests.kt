package com.assignment

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.assignment.data.api.ApiService
import com.assignment.data.repositories.remote.RemoteRepo
import com.assignment.data.repositories.remote.RemoteRepoImpl
import com.assignment.ui.activities.MainActivity
import com.assignment.ui.adapters.AlbumsListAdapter
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.*
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@LargeTest
@RunWith(AndroidJUnit4::class)
class EspressoUITests {

    @get:Rule
    var myActivityRule: ActivityScenarioRule<MainActivity> =
            ActivityScenarioRule(MainActivity::class.java)

    private val server: MockWebServer = MockWebServer()
    private val serverPort = 8000

    private lateinit var apiService: ApiService
    private lateinit var placeholderApi: RemoteRepo

    @Before
    fun init() {
        server.start(serverPort)

        apiService = Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
                .create(ApiService::class.java)
        placeholderApi = RemoteRepoImpl(apiService)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun appContextGivesCorrectPackageName() {
        val appContext: Context = androidx.test.core.app.ApplicationProvider.getApplicationContext()
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

    companion object {
        private fun atPosition(
                position: Int,
                itemMatcher: Matcher<View?>
        ): Matcher<View?> {
            return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
                override fun describeTo(description: Description) {
                    description.appendText("has item at position $position: ")
                    itemMatcher.describeTo(description)
                }

                override fun matchesSafely(view: RecyclerView): Boolean {
                    val viewHolder = view.findViewHolderForAdapterPosition(position)
                            ?: // has no item on such position
                            return false
                    return itemMatcher.matches(viewHolder.itemView)
                }
            }
        }
    }

}
