package com.freshworks.smartlog.ui


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
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
class MainActivityTest2 {

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
    fun mainActivityTest2() {
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

        val textView = onView(
            allOf(
                withId(R.id.logbook_title), withText("test1"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.cardview),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("test1")))

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val cardView = onView(
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
        cardView.perform(click())

        val floatingActionButton2 = onView(
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
        textInputEditText3.perform(replaceText("entry1"), closeSoftKeyboard())

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.description_editText),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.description_titleLayout),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText4.perform(replaceText("description1"), closeSoftKeyboard())

//        pressBack()
//
//        val appCompatButton2 = onView(
//            allOf(
//                withId(R.id.dateButton), withText("2019-02-12"),
//                childAtPosition(
//                    childAtPosition(
//                        withClassName(`is`("android.widget.LinearLayout")),
//                        2
//                    ),
//                    0
//                )
//            )
//        )
//        appCompatButton2.perform(scrollTo(), click())

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

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

        val textView2 = onView(
            allOf(
                withId(R.id.logEntry_title), withText("entry1"),
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
        textView2.check(matches(withText("entry1")))

        val textView3 = onView(
            allOf(
                withId(R.id.logEntry_description), withText("description1"),
                childAtPosition(
                    allOf(
                        withId(R.id.container_layout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("description1")))

        val textView4 = onView(
            allOf(
                withId(R.id.logEntry_description), withText("description1"),
                childAtPosition(
                    allOf(
                        withId(R.id.container_layout),
                        childAtPosition(
                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("description1")))

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val relativeLayout = onView(
            allOf(
                withId(R.id.container_layout),
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
        relativeLayout.perform(click())

        val textView5 = onView(
            allOf(
                withId(R.id.logBook_title_content), withText("test1"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.ScrollView::class.java),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView5.check(matches(withText("test1")))

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val appCompatImageView = onView(
            allOf(
                withId(R.id.edit_log),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageView.perform(click())

        val textInputEditText5 = onView(
            allOf(
                withId(R.id.title_editText), withText("entry1"),
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
        textInputEditText5.perform(replaceText("entry1 modified"))

        val textInputEditText6 = onView(
            allOf(
                withId(R.id.title_editText), withText("entry1 modified"),
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
        textInputEditText6.perform(closeSoftKeyboard())

        //pressBack()

        val editText = onView(
            allOf(
                withId(R.id.title_editText), withText("entry1 modified"),
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
        editText.check(matches(withText("entry1 modified")))

        pressBack()

        val appCompatImageView2 = onView(
            allOf(
                withId(R.id.edit_log),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatImageView2.perform(click())

        val textInputEditText7 = onView(
            allOf(
                withId(R.id.title_editText), withText("entry1"),
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
        textInputEditText7.perform(replaceText("entry1 modified"))

        val textInputEditText8 = onView(
            allOf(
                withId(R.id.title_editText), withText("entry1 modified"),
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
        textInputEditText8.perform(closeSoftKeyboard())


        val appCompatButton4 = onView(
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
        appCompatButton4.perform(scrollTo(), click())

        val textView6 = onView(
            allOf(
                withId(R.id.title), withText("entry1 modified"),
                childAtPosition(
                    childAtPosition(
                        IsInstanceOf.instanceOf(android.widget.ScrollView::class.java),
                        0
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        textView6.check(matches(withText("entry1 modified")))

        val appCompatImageView3 = onView(
            allOf(
                withId(R.id.delete_log),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatImageView3.perform(click())

        val appCompatButton5 = onView(
            allOf(
                withId(android.R.id.button1), withText("Yes"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.buttonPanel),
                        0
                    ),
                    3
                )
            )
        )
        appCompatButton5.perform(scrollTo(), click())

//        val textView7 = onView(
//            allOf(
//                withId(R.id.logEntry_title), withText("entry1"),
//                childAtPosition(
//                    allOf(
//                        withId(R.id.container_layout),
//                        childAtPosition(
//                            IsInstanceOf.instanceOf(android.widget.LinearLayout::class.java),
//                            0
//                        )
//                    ),
//                    0
//                ),
//                isDisplayed()
//            )
//        )
//        textView7.check(matches(withText("entry1")))
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
