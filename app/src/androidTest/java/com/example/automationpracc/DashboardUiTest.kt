package com.example.automationpracc

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun navigateToDashboard() {
        // Log in before every test
        composeTestRule.onNodeWithTag("username_field").performTextInput("admin")
        composeTestRule.onNodeWithTag("password_field").performTextInput("password123")
        composeTestRule.onNodeWithTag("login_button").performClick()
        
        // Wait for dashboard to load
        composeTestRule.waitUntil(7000) {
            composeTestRule.onAllNodes(hasTestTag("search_bar")).fetchSemanticsNodes().isNotEmpty()
        }
    }

    @Test
    fun testCheckboxToggle() {
        val itemTag = "checkbox_Verifying Checkboxes"
        
        // 1. Initially unchecked (assertIsOff)
        composeTestRule.onNodeWithTag(itemTag).assertIsOff()
        
        // 2. Click and verify checked (assertIsOn)
        composeTestRule.onNodeWithTag(itemTag).performClick()
        composeTestRule.onNodeWithTag(itemTag).assertIsOn()
    }

    @Test
    fun testEmptySearchState() {
        // 1. Search for something non-existent
        composeTestRule.onNodeWithTag("search_bar").performTextInput("NonExistentItem123")
        
        // 2. Verify specific items are NOT displayed
        composeTestRule.onNodeWithText("Interacting with Buttons").assertDoesNotExist()
    }

    @Test
    fun testScrollToBottom() {
        // 1. Attempt to scroll to the last item in the list
        val lastItemTag = "item_Network Error States"
        
        // We target the LazyColumn (items_list) and tell it to scroll until it finds the tag
        composeTestRule.onNodeWithTag("items_list")
            .performScrollToNode(hasTestTag(lastItemTag))
            
        // 2. Verify it's now visible
        composeTestRule.onNodeWithTag(lastItemTag).assertIsDisplayed()
    }

    @Test
    fun testSearchAndClear() {
        val searchBar = composeTestRule.onNodeWithTag("search_bar")
        
        // 1. Search for "Data"
        searchBar.performTextInput("Data")
        composeTestRule.onNodeWithText("Data Persistence").assertIsDisplayed()
        composeTestRule.onNodeWithText("Interacting with Buttons").assertDoesNotExist()
        
        // 2. Clear search and verify all items return
        searchBar.performTextClearance()
        composeTestRule.onNodeWithText("Interacting with Buttons").assertIsDisplayed()
    }
}
