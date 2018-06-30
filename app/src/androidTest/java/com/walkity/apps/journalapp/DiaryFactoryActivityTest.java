package com.walkity.apps.journalapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.walkity.apps.journalapp.addeditdiary.DiaryFactoryActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by alkaj on 6/27/18.
 * Updates and create diary activity test class
 */
@RunWith(AndroidJUnit4.class)
public class DiaryFactoryActivityTest {
    @Rule
    public ActivityTestRule<DiaryFactoryActivity> dfatr
            = new ActivityTestRule(DiaryFactoryActivity.class);

    @Test
    public void showsTitleInput()
    {
        onView(withId(R.id.textInputLayout)).check(matches(isDisplayed()));
    }
}