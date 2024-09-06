package com.example.pharmaecomapp.data



data class LoginResponse(
    val data: UserData,
    val error: Boolean,
    val msg: String
) {

    data class UserData(
        val id: Int,
        val company_id: Int,
        val user_type: String,
        val name: String,
        val mobile: String,
        val address: String?,
        val gst: String?,
        val created_at: String,
        val updated_at: String,
        val state: String,
        val city: String?,
        val pincode: String?,
        val country: String,
        val password: String,
        val token: String,
        val dob: String,
        val doa: String,
        val order_type: String,
        val mode_of_transport: Int,
        val division: String,
        val email: String?,
        val source: String?,
        val date: String?,
        val remarks: String?,
        val status: Int,
        val remind_time: String?,
        val remind_date: String?,
        val user_id: Int,
        val alternate_phone: String?
    )
}