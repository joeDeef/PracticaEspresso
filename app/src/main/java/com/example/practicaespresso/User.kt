// User.kt
package com.example.practicaespresso

/**
 * Data class representing a user in the application.
 *
 * @property name The user's full name.
 * @property age The user's age.
 * @property email The user's email address, used for login.
 * @property password The user's password, used for login.
 */
data class User(val name: String, val age: Int, val email: String, val password: String)