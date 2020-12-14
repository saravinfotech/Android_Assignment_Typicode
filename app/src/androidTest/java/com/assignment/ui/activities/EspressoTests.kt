package com.assignment.ui.activities


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.assignment.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EspressoTests {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun espressoTests() {


        val imageView = onView(
            allOf(
                withContentDescription("Image Here"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))

        val editText = onView(
            allOf(
                withId(R.id.searchBoxEdt), withText("Search"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        editText.check(matches(withText("Search")))

        val imageView2 = onView(
            allOf(
                withId(R.id.cancel), withContentDescription("Image Here"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        imageView2.check(matches(isDisplayed()))

        val imageView3 = onView(
            allOf(
                withId(R.id.image),
                withParent(withParent(withId(R.id.parentView))),
                isDisplayed()
            )
        )
        imageView3.check(matches(isDisplayed()))

        val textView3 = onView(
            allOf(
                withId(R.id.text), withText("adipisci laborum fuga laboriosam"),
                withParent(withParent(withId(R.id.parentView))),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("adipisci laborum fuga laboriosam")))

        val textView4 = onView(
            allOf(
                withId(R.id.text), withText("adipisci laborum fuga laboriosam"),
                withParent(withParent(withId(R.id.parentView))),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("adipisci laborum fuga laboriosam")))

        val recyclerView = onView(
            allOf(
                withId(R.id.recView),
                childAtPosition(
                    withClassName(`is`("android.widget.LinearLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val imageButton = onView(
            allOf(
                withContentDescription("Navigate up"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageButton.check(matches(isDisplayed()))

        val textView5 = onView(
            allOf(
                withText("Details"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("Details")))

        val linearLayout = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.detailsView),
                        withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout.check(matches(isDisplayed()))

        val textView6 = onView(
            allOf(
                withText("dolorum ut in voluptas mollitia et saepe quo animi"),
                withParent(withParent(withId(R.id.detailsView))),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("dolorum ut in voluptas mollitia et saepe quo animi")))

        val textView7 = onView(
            allOf(
                withText("Title"),
                withParent(withParent(withId(R.id.detailsView))),
                isDisplayed()
            )
        )
        textView7.check(matches(withText("Title")))

        val textView8 = onView(
            allOf(
                withText("aut dicta possimus sint mollitia voluptas commodi quo doloremque\niste corrupti reiciendis voluptatem eius rerum\nsit cumque quod eligendi laborum minima\nperferendis recusandae assumenda consectetur porro architecto ipsum ipsam"),
                withParent(withParent(withId(R.id.detailsView))),
                isDisplayed()
            )
        )
        textView8.check(matches(withText("aut dicta possimus sint mollitia voluptas commodi quo doloremque iste corrupti reiciendis voluptatem eius rerum sit cumque quod eligendi laborum minima perferendis recusandae assumenda consectetur porro architecto ipsum ipsam")))

        val textView9 = onView(
            allOf(
                withText("aut dicta possimus sint mollitia voluptas commodi quo doloremque\niste corrupti reiciendis voluptatem eius rerum\nsit cumque quod eligendi laborum minima\nperferendis recusandae assumenda consectetur porro architecto ipsum ipsam"),
                withParent(withParent(withId(R.id.detailsView))),
                isDisplayed()
            )
        )
        textView9.check(matches(withText("aut dicta possimus sint mollitia voluptas commodi quo doloremque iste corrupti reiciendis voluptatem eius rerum sit cumque quod eligendi laborum minima perferendis recusandae assumenda consectetur porro architecto ipsum ipsam")))

        val textView10 = onView(
            allOf(
                withText("Description"),
                withParent(withParent(withId(R.id.detailsView))),
                isDisplayed()
            )
        )
        textView10.check(matches(withText("Description")))

        val textView11 = onView(
            allOf(
                withText("User Details"),
                withParent(
                    allOf(
                        withId(R.id.userDetails),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView11.check(matches(withText("User Details")))

        val linearLayout2 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.userDetails),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout2.check(matches(isDisplayed()))

        val textView12 = onView(
            allOf(
                withId(R.id.username), withText("Ervin Howell"),
                withParent(withParent(withId(R.id.userDetails))),
                isDisplayed()
            )
        )
        textView12.check(matches(withText("Ervin Howell")))

        val textView13 = onView(
            allOf(
                withText("User name"),
                withParent(withParent(withId(R.id.userDetails))),
                isDisplayed()
            )
        )
        textView13.check(matches(withText("User name")))

        val linearLayout3 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.userDetails),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout3.check(matches(isDisplayed()))

        val textView14 = onView(
            allOf(
                withId(R.id.userEmail), withText("Shanna@melissa.tv"),
                withParent(withParent(withId(R.id.userDetails))),
                isDisplayed()
            )
        )
        textView14.check(matches(withText("Shanna@melissa.tv")))

        val textView15 = onView(
            allOf(
                withText("Email Address"),
                withParent(withParent(withId(R.id.userDetails))),
                isDisplayed()
            )
        )
        textView15.check(matches(withText("Email Address")))

        val linearLayout4 = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.userDetails),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout4.check(matches(isDisplayed()))

        val textView16 = onView(
            allOf(
                withText("010-692-6593 x09125"),
                withParent(withParent(withId(R.id.userDetails))),
                isDisplayed()
            )
        )
        textView16.check(matches(withText("010-692-6593 x09125")))

        val textView17 = onView(
            allOf(
                withText("Phone Number"),
                withParent(withParent(withId(R.id.userDetails))),
                isDisplayed()
            )
        )
        textView17.check(matches(withText("Phone Number")))

        val linearLayout5 = onView(
            allOf(
                withId(R.id.visitWebsite),
                withParent(
                    allOf(
                        withId(R.id.userDetails),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        linearLayout5.check(matches(isDisplayed()))

        val textView18 = onView(
            allOf(
                withText("Visit Website"),
                withParent(
                    allOf(
                        withId(R.id.visitWebsite),
                        withParent(withId(R.id.userDetails))
                    )
                ),
                isDisplayed()
            )
        )
        textView18.check(matches(withText("Visit Website")))

        val textView19 = onView(
            allOf(
                withId(R.id.albumPhotosManager), withText("Show Album Photos"),
                withParent(withParent(withId(R.id.detailsView))),
                isDisplayed()
            )
        )
        textView19.check(matches(withText("Show Album Photos")))

        val view = onView(
            allOf(
                withParent(
                    allOf(
                        withId(R.id.userDetails),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        view.check(matches(isDisplayed()))

        val view2 = onView(
            allOf(
                withParent(withParent(withId(R.id.detailsView))),
                isDisplayed()
            )
        )
        view2.check(matches(isDisplayed()))

        val materialTextView = onView(
            allOf(
                withId(R.id.albumPhotosManager), withText("Show Album Photos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.detailsView),
                        2
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialTextView.perform(click())

        val imageView4 = onView(
            allOf(
                withId(R.id.image),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))),
                isDisplayed()
            )
        )
        imageView4.check(matches(isDisplayed()))

        val textView20 = onView(
            allOf(
                withId(R.id.albumPhotosManager), withText("Hide Album Photos"),
                withParent(withParent(withId(R.id.detailsView))),
                isDisplayed()
            )
        )
        textView20.check(matches(withText("Hide Album Photos")))

        val materialTextView2 = onView(
            allOf(
                withId(R.id.albumPhotosManager), withText("Hide Album Photos"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.detailsView),
                        2
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        materialTextView2.perform(click())

        val textView21 = onView(
            allOf(
                withId(R.id.commentsManager), withText("Show Comments"),
                withParent(withParent(withId(R.id.detailsView))),
                isDisplayed()
            )
        )
        textView21.check(matches(withText("Show Comments")))

        val materialTextView3 = onView(
            allOf(
                withId(R.id.commentsManager), withText("Show Comments"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.detailsView),
                        2
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialTextView3.perform(click())

        val textView22 = onView(
            allOf(
                withId(R.id.text), withText("et ea vero quia laudantium autem"),
                withParent(withParent(withId(R.id.parentView))),
                isDisplayed()
            )
        )
        textView22.check(matches(withText("et ea vero quia laudantium autem")))

        val textView23 = onView(
            allOf(
                withText("Details"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        textView23.check(matches(withText("Details")))

        val imageButton2 = onView(
            allOf(
                withContentDescription("Navigate up"),
                withParent(
                    allOf(
                        withId(R.id.toolbar),
                        withParent(IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java))
                    )
                ),
                isDisplayed()
            )
        )
        imageButton2.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
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
}
