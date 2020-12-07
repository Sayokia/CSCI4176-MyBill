package com.example.mybill.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val login_success: String? = null,
    val login_error: String? = null,

    val sign_success: String? = null,
    val sign_error: String? = null
) {

}