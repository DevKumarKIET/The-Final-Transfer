package com.example.thefinaltransfer.presentation.navoptions.aboutscreen.aboutcomponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AboutViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AboutUiState())
    val uiState: StateFlow<AboutUiState> = _uiState.asStateFlow()

    init {
        loadAboutData()
    }

    private fun loadAboutData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Populating data based on design specifications
            val howItWorks = listOf(
                HowItWorksItem(
                    stepNumber = 1,
                    title = "Create Encrypted Packets",
                    description = "Upload files, photos, and messages into secure, military-grade encrypted packets.",
                    gradientColors = listOf(Color(0xFFFFA62A), Color(0xFFFFB703))
                ),
                HowItWorksItem(
                    stepNumber = 2,
                    title = "Assign Verified Nominees",
                    description = "Choose KYC-verified individuals to receive each packet securely.",
                    gradientColors = listOf(Color(0xFFFF9F45), Color(0xFFFFA62A))
                ),
                HowItWorksItem(
                    stepNumber = 3,
                    title = "Configure Triggers",
                    description = "Set inactivity periods and verification methods for automatic packet release.",
                    gradientColors = listOf(Color(0xFFFFB703), Color(0xFFFF9F45))
                ),
                HowItWorksItem(
                    stepNumber = 4,
                    title = "Automatic Transfer",
                    description = "Packets are automatically delivered when triggers activate, ensuring your legacy lives on.",
                    gradientColors = listOf(Color(0xFFCD5052), Color(0xFFFFA62A))
                )
            )

            val trustFeatures = listOf(
                TrustFeatureItem(
                    icon = Icons.Outlined.Lock,
                    title = "Military-Grade Encryption",
                    description = "AES-256 encryption protects your files with the highest security standard used worldwide."
                ),
                TrustFeatureItem(
                    icon = Icons.Outlined.Group,
                    title = "Trusted Nominees",
                    description = "Designate verified family members or trusted individuals to receive your digital legacy."
                ),
                TrustFeatureItem(
                    icon = Icons.Outlined.Schedule,
                    title = "Regular Check-ins",
                    description = "Configurable check-in system ensures packets are only transferred when needed, with customizable frequency and reminders."
                ),
                TrustFeatureItem(
                    icon = Icons.Outlined.VerifiedUser,
                    title = "Trusted Users System",
                    description = "Add trusted users who can verify your check-ins and assist in emergency situations, with priority user designation."
                ),
                TrustFeatureItem(
                    icon = Icons.Outlined.VisibilityOff,
                    title = "Zero-Knowledge Privacy",
                    description = "We never access your encrypted files. Only you and your nominees hold the decryption keys."
                ),
                TrustFeatureItem(
                    icon = Icons.Outlined.Folder,
                    title = "Categorized Packets",
                    description = "Organize your digital legacy into Personal, Organization, Emergency, and Other categories for better management."
                ),
                TrustFeatureItem(
                    icon = Icons.Outlined.Send,
                    title = "Instant Transfer",
                    description = "When triggered, encrypted packets are delivered immediately and securely to nominees."
                ),
                TrustFeatureItem(
                    icon = Icons.Outlined.FavoriteBorder,
                    title = "Smart Notifications",
                    description = "Email and SMS reminders ensure you never miss a check-in, with customizable notification preferences."
                ),
                TrustFeatureItem(
                    icon = Icons.Outlined.Favorite,
                    title = "Peace of Mind",
                    description = "Rest assured knowing your most important files will reach the right people at the right time."
                )
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    howItWorksItems = howItWorks,
                    trustFeatures = trustFeatures
                )
            }
        }
    }
}

