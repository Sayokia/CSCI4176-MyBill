package com.example.mybill.addBill

/**
 * Authentication result : success (user details) or error message.
 */
data class AddBillResult(
    val success: String? = null,
    val error: String? = null,

) {

}