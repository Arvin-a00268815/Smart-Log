package com.freshworks.smartlog.ui


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import com.freshworks.smartlog.R
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
class MainActivityTestForMoveLog {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )

    @Test
    fun mainActivityTestForMoveLog() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content_frame),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val textInputEditText = onView(
            allOf(
                withId(R.id.title_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.title_textLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(click())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.title_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.title_textLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("test1"), closeSoftKeyboard())

        val appCompatButton = onView(
            allOf(
                withId(R.id.create_book_button), withText("Create Log Book"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content_frame),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.title_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.title_textLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(click())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.title_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.title_textLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("destination"), closeSoftKeyboard())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.create_book_button), withText("Create Log Book"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val cardView = onView(
            allOf(
                withId(R.id.cardview),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view),
                        1
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView.perform(click())

        val floatingActionButton3 = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content_frame),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton3.perform(click())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.title_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.title_textLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText5.perform(replaceText("log1"), closeSoftKeyboard())

        val appCompatButton3 = onView(
            allOf(
                withId(R.id.submit_button), withText("submit"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.ScrollView")),
                        0
                    ),
                    2
                )
            )
        )
        appCompatButton3.perform(scrollTo(), click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2000)

        val appCompatImageView = onView(
            allOf(
                withId(R.id.more_action_view),
                childAtPosition(
                    allOf(
                        withId(R.id.container_layout),
                        childAtPosition(
                            withClassName(`is`("android.widget.LinearLayout")),
                            0
                        )
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val appCompatTextView = onView(
            allOf(
                withId(R.id.title), withText("Move"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatTextView.perform(click())

//        val linearLayout = onView(
//            allOf(
//                withId(R.id.parent_container),
//                childAtPosition(
//                    childAtPosition(
//                        withId(R.id.recycler_view),
//                        0
//                    ),
//                    0
//                ),
//                isDisplayed()
//            )
//        )
//        linearLayout.perform(click(), closeSoftKeyboard())
        pressBack()
        pressBack()

//        val appCompatImageButton = onView(
//            allOf(
//                childAtPosition(
//                    allOf(
//                        withId(R.id.toolbar),
//                        childAtPosition(
//                            withId(R.id.appbar),
//                            0
//                        )
//                    ),
//                    1
//                ),
//                isDisplayed()
//            )
//        )
//        appCompatImageButton.perform(click())

        val cardView2 = onView(
            allOf(
                withId(R.id.cardview),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        cardView2.perform(click())

        val textView = onView(
            allOf(
                withContentDescription("log1"), withText("log1"),
                childAtPosition(
                    allOf(
                        withId(R.id.container_layout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("log1")))
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
