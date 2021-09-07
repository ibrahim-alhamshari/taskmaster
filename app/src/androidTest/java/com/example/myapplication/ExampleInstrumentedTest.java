package com.example.myapplication;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.content.Context;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import androidx.test.filters.LargeTest;

import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.*;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void listGoesOverTheFold() {
        onView(withText("Tasks app")).check(matches(isDisplayed()));
        onView(withText("Add Task")).check(matches(isDisplayed()));
        onView(withText("SETTINGS")).check(matches(isDisplayed()));
        onView(withId(R.id.mainPageRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.userNameHomePage)).check(matches(isDisplayed()));
    }

    @Test
    public void checkId(){
        onView(withId(R.id.addTaskButton)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.submitInAddDish)).check(matches(isDisplayed()));
        onView(withId(R.id.titleInAddTask)).check(matches(isDisplayed()));
        onView(withId(R.id.bodyInAddTask)).check(matches(isDisplayed()));
        onView(withId(R.id.stateInAddTask)).check(matches(isDisplayed()));
    }

    @Test
    public void settingsButton(){
        onView(withId(R.id.homePageSettingsButton)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.userNameFromSettings)).check(matches(isDisplayed())).perform(typeText("Ali"));
        onView(withId(R.id.settingsSaveButton)).check(matches(isDisplayed())).perform(click());
        onView(withText("Ali’s tasks")).check(matches(isDisplayed()));
    }

    @Test
    public void addTaskButton() {
        onView(withId(R.id.addTaskButton)).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.titleInAddTask)).check(matches(isDisplayed())).perform(typeText("Humam3"));
        onView(withId(R.id.bodyInAddTask)).check(matches(isDisplayed())).perform(typeText("Espresso doing"));
        onView(withId(R.id.stateInAddTask)).check(matches(isDisplayed())).perform(typeText("Completed"));
        onView(withId(R.id.submitInAddDish)).check(matches(isDisplayed())).perform(click());
        onView(withText("Humam3")).check(matches(isDisplayed()));
    }

    @Test
    public void seeMoreButton(){
        onView(withId(R.id.seeMoreInMainPage)).check(matches(isDisplayed())).perform(click());
        onView(withText("Humam1")).check(matches(isDisplayed()));
        onView(withText("Espresso doing")).check(matches(isDisplayed()));
        onView(withText("Completed")).check(matches(isDisplayed()));
    }
}