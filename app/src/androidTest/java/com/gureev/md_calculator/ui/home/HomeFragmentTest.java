package com.gureev.md_calculator.ui.home;

import androidx.test.rule.ActivityTestRule;

import com.gureev.md_calculator.MainActivity;
import com.gureev.md_calculator.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class HomeFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testVievComponts() {
        onView(withId(R.id.home_button_plus))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_minus))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_mul))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_div))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_editText_result))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_editText_expression))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_dot))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_save))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_del))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_0))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_1))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_2))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_3))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_4))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_5))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_6))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_7))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_8))
                .check(matches(isDisplayed()));
        onView(withId(R.id.home_button_9))
                .check(matches(isDisplayed()));
    }
}