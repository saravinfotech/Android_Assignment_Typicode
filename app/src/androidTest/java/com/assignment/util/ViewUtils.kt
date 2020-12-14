package com.assignment.util

import android.widget.LinearLayout
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.assignment.R
import org.hamcrest.Matchers
import org.hamcrest.core.IsInstanceOf


fun matchesText(text: String) = ViewAssertions.matches(ViewMatchers.withText(text))

fun viewMatcher(text: String, resourceId: Int) = Matchers.allOf(
    ViewMatchers.withId(R.id.sort_down),
    ViewMatchers.withContentDescription(text),
    ViewMatchers.withParent(ViewMatchers.withParent(ViewMatchers.withId(resourceId))),
    ViewMatchers.isDisplayed()
)

fun viewMatcher(text: String, resourceId: Int, className: Class<LinearLayout>) = Matchers.allOf(
    ViewMatchers.withText(text),
    ViewMatchers.withParent(
        Matchers.allOf(
            ViewMatchers.withId(resourceId),
            ViewMatchers.withParent(IsInstanceOf.instanceOf(className))
        )
    ),
    ViewMatchers.isDisplayed()
)