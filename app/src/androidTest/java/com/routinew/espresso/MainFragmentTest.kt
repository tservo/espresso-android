package com.routinew.espresso

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.routinew.espresso.ui.main.MainFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {
    @Test
    fun testMainFragment() {
        val scenario = launchFragmentInContainer {
            MainFragment()
        }
    }
}