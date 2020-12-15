package com.assignment.util

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.assignment.R
import com.assignment.ui.adapters.AlbumsListAdapter
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf


fun matchesText(text: String) = ViewAssertions.matches(ViewMatchers.withText(text))

fun viewMatcher(text: String, resourceId: Int) = Matchers.allOf(
    ViewMatchers.withId(R.id.sort_down),
    ViewMatchers.withContentDescription(text),
    ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(resourceId))),
    ViewMatchers.isDisplayed()
)

fun <T> viewMatcher(text: String, resourceId: Int, className: Class<T>) = Matchers.allOf(
    ViewMatchers.withText(text),
    ViewMatchers.withParent(
        Matchers.allOf(
            ViewMatchers.withId(resourceId),
            ViewMatchers.withParent(IsInstanceOf.instanceOf(className))
        )
    ),
    ViewMatchers.isDisplayed()
)

fun childAtPosition(
    parentMatcher: Matcher<View>, position: Int
): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("Child at position $position in parent ")
            parentMatcher.describeTo(description)
        }

        public override fun matchesSafely(view: View): Boolean {
            val parent = view.parent
            return parent is ViewGroup && parentMatcher.matches(parent)
                    && view == parent.getChildAt(position)
        }
    }
}

fun matcherForAmbiguousElements(viewToBeFound: Int, text: String, parentView: Int) = Matchers.allOf(
    ViewMatchers.withId(viewToBeFound),
    ViewMatchers.withContentDescription(text),
    childAtPosition(
        childAtPosition(
            ViewMatchers.withId(parentView),
            1
        ),
        0
    ),
    ViewMatchers.isDisplayed()
)

fun sleep() {
    sleep(500)
}

fun sleep(milliseconds: Long) {
    Thread.sleep(milliseconds)
}

fun verifyElementsInAlbumList(textToBeChecked: String) = Espresso.onView(
    Matchers.allOf(
        ViewMatchers.withId(R.id.text), ViewMatchers.withText(textToBeChecked),
        ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(R.id.parentView))),
        ViewMatchers.isDisplayed()
    )
)


fun getTitleMenu(menu: String): ViewInteraction? {
    val titleMenu = Espresso.onView(
        viewMatcher(menu, R.id.toolbar)
    )
    titleMenu.check(matchesText(""))
    return titleMenu
}

fun clickFirstItemInAlbum() {
    sleep()
    Espresso.onView(ViewMatchers.withId(R.id.albumListRecyclerView))
        .perform(
            RecyclerViewActions.actionOnItemAtPosition<AlbumsListAdapter.ViewClass>(
                0,
                ViewActions.click()
            )
        )
}
