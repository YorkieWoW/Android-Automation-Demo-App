package com.example.automationpracc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.automationpracc.ui.theme.AutomationPraccTheme
import com.example.automationpracc.ui.theme.BackgroundDark
import com.example.automationpracc.ui.theme.BackgroundLight
import com.example.automationpracc.ui.theme.PrimaryDark
import com.example.automationpracc.ui.theme.PrimaryLight
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun lightTheme_appliesCorrectColours() {
        var primaryColour: Color = Color.Unspecified
        var backgroundColour: Color = Color.Unspecified

        composeTestRule.setContent {
            AutomationPraccTheme(darkTheme = false, dynamicColor = false) {
                // The Surface component draws the background color to the screen
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    primaryColour = MaterialTheme.colorScheme.primary
                    backgroundColour = MaterialTheme.colorScheme.background
                    Text("Testing Light Theme - Visual Check")
                }
            }
        }

        // Add a small wait so we can see the Light Theme apply
        Thread.sleep(8000)

        // Verify that the colours from MaterialTheme match our Light palette in Colour.kt
        assertEquals(PrimaryLight, primaryColour)
        assertEquals(BackgroundLight, backgroundColour)
    }

    @Test
    fun darkTheme_appliesCorrectColours() {
        var primaryColour: Color = Color.Unspecified
        var backgroundColour: Color = Color.Unspecified

        composeTestRule.setContent {
            AutomationPraccTheme(darkTheme = true, dynamicColor = false) {
                // The Surface component draws the background color to the screen
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    primaryColour = MaterialTheme.colorScheme.primary
                    backgroundColour = MaterialTheme.colorScheme.background
                    Text("Testing Dark Theme - Visual Check")
                }
            }
        }

        // Another delay so we can see the Dark Theme apply
        Thread.sleep(8000)

        // Verify that the colours from MaterialTheme match our Dark palette in Colour.kt
        assertEquals(PrimaryDark, primaryColour)
        assertEquals(BackgroundDark, backgroundColour)
    }
}
