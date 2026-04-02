package com.example.thefinaltransfer.presentation.navoptions.profilescreen.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState

    fun onLogoutClick() {
        _uiState.update { it.copy(showLogoutDialog = true) }
    }

    fun dismissLogout() {
        _uiState.update { it.copy(showLogoutDialog = false) }
    }

    fun confirmLogout() {
        // TODO logout logic
    }

    fun onEditProfile() {
        // TODO open edit screen
    }
}