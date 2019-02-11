package com.freshworks.smartlog


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
import com.freshworks.smartlog.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LogEntryTest {

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
    fun logEntryTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(700)

        val floatingActionButton = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.title), withText("1test2019-02-10 10:46:15"),
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
        textView.check(matches(withText("1test2019-02-10")))

        val appCompatTextView = onView(
            allOf(
                withId(R.id.title), withText("1test2019-02-10 10:46:15"),
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
        appCompatTextView.perform(click())

        val floatingActionButton2 = onView(
            allOf(
                withId(R.id.fab),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton2.perform(click())

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
        textInputEditText.perform(replaceText("IT"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                withId(R.id.title_editText), withText("IT"),
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
        textInputEditText2.perform(click())

        val textInputEditText3 = onView(
            allOf(
                withId(R.id.title_editText), withText("IT"),
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
        textInputEditText3.perform(replaceText("NOW"))

        val textInputEditText4 = onView(
            allOf(
                withId(R.id.title_editText), withText("NOW"),
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
        textInputEditText4.perform(closeSoftKeyboard())

        val textInputEditText5 = onView(
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
        textInputEditText5.perform(replaceText("324"), closeSoftKeyboard())

        pressBack()

        val appCompatButton = onView(
            allOf(
                withId(R.id.submit_button), withText("submit"),
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

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(2000)

        val textView2 = onView(
            allOf(
                withId(R.id.description), withText("Content"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.recycler_view),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("324")))

        val appCompatTextView2 = onView(
            allOf(
                withId(R.id.title), withText("1======1==2019-02-10 10:47:56"),
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
        appCompatTextView2.perform(click())

        val textView3 = onView(
            allOf(
                withId(R.id.description), withText("324"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content_frame),
                        2
                    ),
                    3
                ),
                isDisplayed()
            )
        )
        textView3.check(matches(withText("324")))

        val textView4 = onView(
            allOf(
                withId(R.id.title), withText("NOW"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.content_frame),
                        2
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        textView4.check(matches(withText("NOW")))
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
