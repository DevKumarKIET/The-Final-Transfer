package com.example.thefinaltransfer.data.model


data class UserModel(
    val uid: String = "",
    val fullName: String = "",
    val email: String = "",
    val mobile: String = "",
    val passwordHash: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val lastLoginAt: Long = System.currentTimeMillis(),
    val isVerified: Boolean = false
)
