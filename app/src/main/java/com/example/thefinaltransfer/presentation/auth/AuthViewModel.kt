package com.example.thefinaltransfer.presentation.auth


import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thefinaltransfer.data.model.UserModel
import com.example.thefinaltransfer.data.repository.AuthResult
import com.example.thefinaltransfer.data.repository.FirebaseAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthUiState(
    // User input fields (persisted across screens)
    val fullName: String = "",
    val email: String = "",
    val mobile: String = "",
    val password: String = "",

    // OTP state
    val otpCode: String = "",
    val isOtpSent: Boolean = false,
    val verificationMethod: VerificationMethod = VerificationMethod.MOBILE,

    // Loading / Error
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,

    // Navigation triggers (consumed by UI)
    val navigateToOtp: Boolean = false,
    val navigateToHome: Boolean = false,
    val navigateToCreatePassword: Boolean = false,
    val navigateToBiometric: Boolean = false,

    // User profile
    val currentUser: UserModel? = null
)

enum class VerificationMethod { MOBILE, EMAIL }


class AuthViewModel : ViewModel() {

    private val repository = FirebaseAuthRepository()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()


    // INPUT HANDLERS

    fun onFullNameChanged(value: String) {
        _uiState.update { it.copy(fullName = value) }
    }

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value, errorMessage = null) }
    }

    fun onMobileChanged(value: String) {
        if (value.length <= 10 && value.all { it.isDigit() }) {
            _uiState.update { it.copy(mobile = value) }
        }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update { it.copy(password = value, errorMessage = null) }
    }

    fun onOtpChanged(value: String) {
        if (value.length <= 6 && value.all { it.isDigit() }) {
            _uiState.update { it.copy(otpCode = value, errorMessage = null) }
        }
    }

    fun onVerificationMethodChanged(method: VerificationMethod) {
        _uiState.update { it.copy(verificationMethod = method) }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun clearNavigationFlags() {
        _uiState.update {
            it.copy(
                navigateToOtp = false,
                navigateToHome = false,
                navigateToCreatePassword = false,
                navigateToBiometric = false
            )
        }
    }

    //SIGNUP FLOW
    fun sendSignUpOtp(activity: Activity) {
        val state = _uiState.value

        // Choose channel based on selected method
        when (state.verificationMethod) {
            VerificationMethod.MOBILE -> {
                val fullPhoneNumber = "+91${state.mobile}" // India country code
                viewModelScope.launch {
                    repository.sendPhoneOtp(fullPhoneNumber, activity).collect { result ->
                        when (result) {
                            is AuthResult.Loading -> {
                                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                            }
                            is AuthResult.Success -> {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        isOtpSent = true,
                                        successMessage = result.message
                                    )
                                }
                            }
                            is AuthResult.Error -> {
                                _uiState.update {
                                    it.copy(isLoading = false, errorMessage = result.message)
                                }
                            }
                        }
                    }
                }
            }
            VerificationMethod.EMAIL -> {
                // For email OTP, we create the account first (email verification is auto-sent)
                viewModelScope.launch {
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                    // We use a temporary password for now, real one set in CreatePassword screen
                    val tempPassword = "TempPass_${System.currentTimeMillis()}"
                    when (val result = repository.signUpWithEmail(state.email, tempPassword)) {
                        is AuthResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    isOtpSent = true,
                                    successMessage = "Verification email sent to ${state.email}"
                                )
                            }
                        }
                        is AuthResult.Error -> {
                            _uiState.update {
                                it.copy(isLoading = false, errorMessage = result.message)
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    fun verifySignUpOtp() {
        val state = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (state.verificationMethod) {
                VerificationMethod.MOBILE -> {
                    when (val result = repository.verifyPhoneOtp(state.otpCode)) {
                        is AuthResult.Success -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    navigateToCreatePassword = true
                                )
                            }
                        }
                        is AuthResult.Error -> {
                            _uiState.update {
                                it.copy(isLoading = false, errorMessage = result.message)
                            }
                        }
                        else -> {}
                    }
                }
                VerificationMethod.EMAIL -> {
                    // For email, verification is link-based. We just proceed.
                    _uiState.update {
                        it.copy(isLoading = false, navigateToCreatePassword = true)
                    }
                }
            }
        }
    }

    fun completeSignUp(password: String) {
        val state = _uiState.value

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val uid = repository.currentUserId ?: run {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Authentication session expired. Please sign up again.")
                }
                return@launch
            }

            val user = UserModel(
                uid = uid,
                fullName = state.fullName,
                email = state.email,
                mobile = state.mobile,
                passwordHash = repository.hashPassword(password),
                isVerified = true
            )

            when (val result = repository.saveUserToDatabase(user)) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            navigateToBiometric = true,
                            currentUser = user
                        )
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                else -> {}
            }
        }
    }

    // SECTION 3: LOGIN FLOW
    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = repository.signInWithEmail(email, password)) {
                is AuthResult.Success -> {
                    // Update last login timestamp
                    repository.currentUserId?.let { uid ->
                        repository.updateUserFields(uid, mapOf("lastLoginAt" to System.currentTimeMillis()))
                    }
                    _uiState.update {
                        it.copy(isLoading = false, navigateToHome = true)
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                else -> {}
            }
        }
    }

    fun loginWithPhone(phoneNumber: String, password: String, activity: Activity) {
        _uiState.update { it.copy(password = password) }

        viewModelScope.launch {
            val fullPhoneNumber = if (phoneNumber.startsWith("+")) phoneNumber else "+91$phoneNumber"

            repository.sendPhoneOtp(fullPhoneNumber, activity).collect { result ->
                when (result) {
                    is AuthResult.Loading -> {
                        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                    }
                    is AuthResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                navigateToOtp = true,
                                successMessage = result.message
                            )
                        }
                    }
                    is AuthResult.Error -> {
                        _uiState.update {
                            it.copy(isLoading = false, errorMessage = result.message)
                        }
                    }
                }
            }
        }
    }


    fun verifyLoginOtp() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = repository.verifyPhoneOtp(_uiState.value.otpCode)) {
                is AuthResult.Success -> {
                    // Update last login timestamp
                    repository.currentUserId?.let { uid ->
                        repository.updateUserFields(uid, mapOf("lastLoginAt" to System.currentTimeMillis()))
                    }
                    _uiState.update {
                        it.copy(isLoading = false, navigateToHome = true)
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                else -> {}
            }
        }
    }

    //Reset Password
    fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = repository.sendPasswordResetEmail(email)) {
                is AuthResult.Success -> {
                    _uiState.update {
                        it.copy(isLoading = false, successMessage = result.message)
                    }
                }
                is AuthResult.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                else -> {}
            }
        }
    }

    //Singout
    fun signOut() {
        repository.signOut()
        _uiState.update { AuthUiState() } // Reset all state
    }


    fun loadCurrentUser() {
        val uid = repository.currentUserId ?: return

        viewModelScope.launch {
            repository.getUserFromDatabase(uid).collect { user ->
                _uiState.update { it.copy(currentUser = user) }
            }
        }
    }
}
