package com.example.thefinaltransfer.presentation.trusteduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefinaltransfer.data.model.TrustedUserModel
import com.example.thefinaltransfer.data.repository.AuthResult
import com.example.thefinaltransfer.data.repository.TrustedUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TrustedUserUiState(
    val users: List<TrustedUserModel> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class TrustedUserViewModel : ViewModel() {
    private val repository = TrustedUserRepository()

    private val _uiState = MutableStateFlow(TrustedUserUiState())
    val uiState: StateFlow<TrustedUserUiState> = _uiState.asStateFlow()

    init {
        loadTrustedUsers()
    }

    private fun loadTrustedUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.getTrustedUsers().collect { userList ->
                _uiState.update {
                    it.copy(
                        users = userList,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
        }
    }

    fun addTrustedUser(fullName: String, email: String, mobileNumber: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, successMessage = null) }

            // Priority assignment logic: lowest priority number = 1.
            // If we have 2 users, next priority is 3.
            val currentList = _uiState.value.users
            val nextPriority = (currentList.maxOfOrNull { it.priorityOrder } ?: 0) + 1

            val newUser = TrustedUserModel(
                fullName = fullName,
                email = email,
                mobileNumber = mobileNumber,
                priorityOrder = nextPriority
            )

            when (val result = repository.addTrustedUser(newUser)) {
                is AuthResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, successMessage = result.message) }
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
                else -> {}
            }
        }
    }

    fun makePrimary(targetUser: TrustedUserModel) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val currentList = _uiState.value.users.sortedBy { it.priorityOrder }.toMutableList()

            if (currentList.isEmpty() || targetUser.priorityOrder == 1) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            // Find current primary
            val currentPrimary = currentList.find { it.priorityOrder == 1 }

            if (currentPrimary != null) {
                // Swap priorities
                val oldPrimaryPriority = currentPrimary.priorityOrder
                val targetPriority = targetUser.priorityOrder

                // We update target to 1, and old primary to targetPriority
                val updatedTarget = targetUser.copy(priorityOrder = oldPrimaryPriority)
                val updatedOldPrimary = currentPrimary.copy(priorityOrder = targetPriority)

                // Update RTDB (simultaneous but we'll await both)
                repository.updateTrustedUser(updatedTarget)
                repository.updateTrustedUser(updatedOldPrimary)
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun updateExistingUser(userId: String, fullName: String, email: String, mobileNumber: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, successMessage = null) }
            val currentList = _uiState.value.users
            val existingUser = currentList.find { it.id == userId }
            if (existingUser != null) {
                val updatedUser = existingUser.copy(
                    fullName = fullName,
                    email = email,
                    mobileNumber = mobileNumber
                )
                when (val result = repository.updateTrustedUser(updatedUser)) {
                    is AuthResult.Success -> {
                        _uiState.update { it.copy(isLoading = false, successMessage = "Trusted user updated successfully") }
                    }
                    is AuthResult.Error -> {
                        _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                    }
                    else -> {}
                }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = "User not found") }
            }
        }
    }

    fun removeTrustedUser(userId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = repository.removeTrustedUser(userId)) {
                is AuthResult.Success -> {
                    _uiState.update { it.copy(isLoading = false, successMessage = result.message) }
                }
                is AuthResult.Error -> {
                    _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
                else -> {}
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(successMessage = null, errorMessage = null) }
    }
}
