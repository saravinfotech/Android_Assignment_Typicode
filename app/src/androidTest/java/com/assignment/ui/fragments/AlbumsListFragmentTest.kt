package com.assignment.ui.fragments

import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.assignment.R
import com.assignment.atPosition
import com.assignment.ui.BaseTestClass
import com.assignment.util.*
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumsListFragmentTest : BaseTestClass() {

    @Test
    fun testTitleBarFunctions() {
        val pageTitle =
            Espresso.onView(viewMatcher("Albums", R.id.toolbar, LinearLayout::class.java))
        pageTitle.check(matches(withText("Albums")))

        getTitleMenu("Sort Down")
    }

    @Test
    fun testAlbumSorting() {
        //To avoid adding test related code to Application avoiding idling resource
        sleep(3000)
        val firstAlbum = verifyElementsInAlbumList("ab rerum non rerum consequatur ut ea unde")
        firstAlbum.check(matches(withText("ab rerum non rerum consequatur ut ea unde")))
        val actionMenuItemView = Espresso.onView(
            matcherForAmbiguousElements(R.id.sort_down, "Sort Down", R.id.toolbar)
        )
        actionMenuItemView.perform(ViewActions.click())
        sleep()
        val lastAlbum = verifyElementsInAlbumList("voluptates delectus iure iste qui")
        lastAlbum.check(matches(withText("voluptates delectus iure iste qui")))
        sleep()
    }

    @Test
    fun testSearchBar() {
        sleep()

        val searchCancel = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.cancel), ViewMatchers.withContentDescription("Image Here"),
                ViewMatchers.withParent(ViewMatchers.withParent(IsInstanceOf.instanceOf(LinearLayout::class.java))),
                ViewMatchers.isDisplayed()
            )
        )
        searchCancel.check(matches(ViewMatchers.isDisplayed()))

        val materialEditText = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.searchView),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withClassName(Matchers.`is`("android.widget.LinearLayout")),
                        2
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        materialEditText.perform(ViewActions.replaceText("ab r"), ViewActions.closeSoftKeyboard())

        val firstAlbum = verifyElementsInAlbumList("ab rerum non rerum consequatur ut ea unde")
        firstAlbum.check(matches(withText("ab rerum non rerum consequatur ut ea unde")))

        val albumThumbnail = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.image),
                ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(R.id.parentView))),
                ViewMatchers.isDisplayed()
            )
        )
        albumThumbnail.check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
            /**
             * An idea on how the tests can be done using a mock server. This is half baked
             * can be developed into a full fledged solution to handle the mock API response.
             */
    fun recyclerViewAlbumElement1TitleCheck() {
        runBlocking {
            server.apply {
                enqueue(MockResponse().setBody(MockResponseFileReader("albums_response.json").content))
            }
            val response = placeholderApi.getAlbums()
            if (response.isSuccessful) {
                response.body()?.let { models ->
                    val sortedModels = models.sortedBy { it.title }
                    val modelAt1Title = sortedModels[1].title

                    Espresso.onView(ViewMatchers.withId(R.id.albumListRecyclerView))
                        .check(
                            matches(
                                atPosition(
                                    1,
                                    ViewMatchers.hasDescendant(withText(modelAt1Title))
                                )
                            )
                        )
                }
            }
        }
    }
}