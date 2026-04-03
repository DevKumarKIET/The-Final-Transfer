package com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.criticaldoc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for PacketDetailScreen.
 * No Hilt — plain ViewModel().
 * Dummy data pre-loaded in PacketDetailUiState default values.
 */
class CrticialDetialViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PacketDetailUiState())
    val uiState: StateFlow<PacketDetailUiState> = _uiState.asStateFlow()

    // ── Dialog controls ───────────────────────────────────────────────────────

    fun onDeleteClick() {
        _uiState.update { it.copy(showDeleteDialog = true) }
    }

    fun onDeleteDismiss() {
        _uiState.update { it.copy(showDeleteDialog = false) }
    }

    fun onDeleteConfirmed() {
        // TODO: Call repository to delete packet
        // For now just dismiss the dialog
        _uiState.update { it.copy(showDeleteDialog = false) }
    }
}