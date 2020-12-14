package com.assignment.ui.fragments

import android.widget.LinearLayout
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.assignment.BaseTestClass
import com.assignment.R
import com.assignment.util.matchesText
import com.assignment.util.viewMatcher
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumsListFragmentTest : BaseTestClass() {

    @Test
    fun testHeading() {
        val pageTitle =
            Espresso.onView(viewMatcher("Albums", R.id.toolbar, LinearLayout::class.java))
        pageTitle.check(ViewAssertions.matches(withText("Albums")))

        val titleMenu = Espresso.onView(
            viewMatcher("Sort Down", R.id.toolbar)
        )
        titleMenu.check(matchesText(""))
    }
}