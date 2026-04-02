package com.example.thefinaltransfer.presentation.navoptions.profilescreen.model

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingsItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit = {}
)

data class ProfileUiState(
    val userName: String = "User Name",
    val userEmail: String = "example@email.com",
    val checkInStatus: String = "Active",
    val lastVerified: String = "Last verified 2 days ago",
    val isPacketSecured: Boolean = true,
    val showLogoutDialog: Boolean = false
)