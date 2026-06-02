package com.example.automationpracc

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testLoginFailure() {
        // 1. Enter wrong credentials
        composeTestRule.onNodeWithTag("username_field").performTextInput("wrong_user")
        composeTestRule.onNodeWithTag("password_field").performTextInput("wrong_pass")
        composeTestRule.onNodeWithTag("login_button").performClick()

        // 2. Wait for the error message to appear (handles the 1.5s simulated delay)
        // We use a longer timeout (5s) to account for slow emulators
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodes(hasTestTag("error_message")).fetchSemanticsNodes().isNotEmpty()
        }

        // 3. Verify error state
        composeTestRule.onNodeWithTag("error_message").assertIsDisplayed()
        composeTestRule.onNodeWithText("Invalid username or password").assertIsDisplayed()
    }

    @Test
    fun testLoginSuccessAndDashboard() {
        // 1. Enter correct credentials
        composeTestRule.onNodeWithTag("username_field").performTextInput("admin")
        composeTestRule.onNodeWithTag("password_field").performTextInput("password123")
        composeTestRule.onNodeWithTag("login_button").performClick()

        // 2. Wait for navigation to dashboard (search_bar to appear)
        composeTestRule.waitUntil(7000) {
            composeTestRule.onAllNodes(hasTestTag("search_bar")).fetchSemanticsNodes().isNotEmpty()
        }

        // 3. Verify Dashboard content
        composeTestRule.onNodeWithText("Dashboard").assertIsDisplayed()
        
        // 4. Search for an item and verify filtering
        composeTestRule.onNodeWithTag("search_bar").performTextInput("Scrolling")
        // Wait for the specific item to appear after filtering
        composeTestRule.waitUntil(3000) {
             composeTestRule.onAllNodes(hasTestTag("item_Scrolling through Lists")).fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("item_Scrolling through Lists").assertIsDisplayed()

        // 5. Test Dialog (using useUnmergedTree = true for deep elements like Dialogs)
        composeTestRule.onNodeWithTag("item_Scrolling through Lists").performClick()
        composeTestRule.onNodeWithTag("details_dialog", useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithTag("dialog_close_button", useUnmergedTree = true).performClick()
        
        // Ensure dialog is gone
        composeTestRule.onNodeWithTag("details_dialog").assertDoesNotExist()

        // 6. Logout and return to Login
        composeTestRule.onNodeWithTag("logout_button").performClick()
        composeTestRule.onNodeWithTag("login_title").assertIsDisplayed()
    }
}
