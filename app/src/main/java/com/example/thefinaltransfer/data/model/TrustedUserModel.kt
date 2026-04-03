package com.example.thefinaltransfer.data.model

data class TrustedUserModel(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val mobileNumber: String = "",
    val priorityOrder: Int = 0 // 1 = Primary, 2 = Secondary, etc.
)
