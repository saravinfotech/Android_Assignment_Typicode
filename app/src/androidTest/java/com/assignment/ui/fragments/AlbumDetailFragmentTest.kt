package com.assignment.ui.fragments

import android.widget.LinearLayout
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.assignment.BaseTestClass
import com.assignment.R
import com.assignment.util.clickFirstItemInAlbum
import com.assignment.util.sleep
import com.assignment.util.viewMatcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumDetailFragmentTest : BaseTestClass() {


    @Before
    fun setUp() {
        clickFirstItemInAlbum()
    }

    @Test
    fun checkAlbumListClick() {

        val pageTitle =
            onView(viewMatcher("Details", R.id.toolbar, LinearLayout::class.java))
        pageTitle.check(matches(withText("Details")))

        val backButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(IsInstanceOf.instanceOf(LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        backButton.check(matches(isDisplayed()))
    }

    @Test
    fun testDetailsSectionIsDisplayed() {
        onView(withId(R.id.albumTitle))
            .check(matches(withText("dolorum ut in voluptas mollitia et saepe quo animi")))
        onView(withId(R.id.titleLabel))
            .check(matches(withText("Title")))
    }

    @Test
    fun userDetails() {
        sleep()
        onView(withId(R.id.userDetailsHeader))
            .check(matches(withText("User Details")))

        onView(withId(R.id.username))
            .check(matches(withText("Ervin Howell")))

        onView(withId(R.id.userNameLabel))
            .check(matches(withText("User name")))

        onView(withId(R.id.userEmail))
            .check(matches(withText("Shanna@melissa.tv")))

        onView(withId(R.id.userEmailLabel))
            .check(matches(withText("Email Address")))

        onView(withId(R.id.userPhoneNumber))
            .check(matches(withText("010-692-6593 x09125")))

        onView(withId(R.id.userPhoneLabel))
            .check(matches(withText("Phone Number")))
    }


    @Test
    fun verifyButtons() {
        onView(withId(R.id.visitWebsite)).check(matches(isDisplayed()))
        onView(withId(R.id.visitWebsiteLabel))
            .check(matches(withText("Visit Website")))


        onView(withId(R.id.albumPhotosManager)).check(matches(isDisplayed()))
        onView(withId(R.id.albumPhotosManager))
            .check(matches(withText("Show Album Photos")))

        onView(withId(R.id.albumPhotosManager)).perform(click())
        onView(withId(R.id.albumPhotosManager))
            .check(matches(withText("Hide Album Photos")))
        onView(withId(R.id.albumPhotosManager)).perform(click())
    }
}