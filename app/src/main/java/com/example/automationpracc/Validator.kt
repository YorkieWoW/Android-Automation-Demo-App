package com.example.automationpracc

object Validator {
    fun validateLogin(username: String, password: String): Boolean {
        return username == "admin" && password == "password123"
    }

    fun isSearchMatch(item: String, query: String): Boolean {
        return item.contains(query, ignoreCase = true)
    }
}
