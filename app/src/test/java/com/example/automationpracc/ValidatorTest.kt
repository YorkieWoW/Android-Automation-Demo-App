package com.example.automationpracc

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidatorTest {

    @Test
    fun login_withCorrectCredentials_returnsTrue() {
        assertTrue(Validator.validateLogin("admin", "password123"))
    }

    @Test
    fun login_withIncorrectCredentials_returnsFalse() {
        assertFalse(Validator.validateLogin("wrong", "password123"))
        assertFalse(Validator.validateLogin("admin", "wrong"))
    }

    @Test
    fun searchMatch_whenQueryMatches_returnsTrue() {
        assertTrue(Validator.isSearchMatch("Apple Pie", "apple"))
        assertTrue(Validator.isSearchMatch("Banana Split", "SPLIT"))
    }

    @Test
    fun searchMatch_whenQueryDoesNotMatch_returnsFalse() {
        assertFalse(Validator.isSearchMatch("Apple Pie", "Orange"))
    }
}
