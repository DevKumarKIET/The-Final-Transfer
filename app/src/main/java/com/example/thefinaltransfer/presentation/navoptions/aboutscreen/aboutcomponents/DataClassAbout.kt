package com.example.thefinaltransfer.presentation.navoptions.aboutscreen.aboutcomponents

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class AboutUiState(
    val isLoading: Boolean = false,
    val howItWorksItems: List<HowItWorksItem> = emptyList(),
    val trustFeatures: List<TrustFeatureItem> = emptyList()
)

data class TrustFeatureItem(
    val icon: ImageVector,
    val title: String,
    val description: String
)

data class HowItWorksItem(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val gradientColors: List<Color>
)