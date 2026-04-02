package com.example.thefinaltransfer.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object SplashScreen : Routes()

    @Serializable
    data object StartingScreen : Routes()

    @Serializable
    data object LoginScreen : Routes()
    @Serializable
    data object EmailLoginScreen : Routes()
    @Serializable
    data object MobileLoginScreen : Routes()
    @Serializable
    data class LoginOTPScreen(
        val identifier: String = "",
        val method: String = "phone"
    ) : Routes()
    @Serializable
    data object SignUpScreen : Routes()
    @Serializable
    data class SignUpOTPScreen(
        val email: String = "",
        val mobile: String = "",
        val fullName: String = ""
    ) : Routes()

    @Serializable
    data object CreatePasswordScreen : Routes()

    @Serializable
    data object BiometricAuthenticationScreen : Routes()

    @Serializable
    data object HomeScreen : Routes()
    @Serializable
    data object VaultScreen : Routes()

    @Serializable
    data object UploadScreen: Routes()

    @Serializable
    data object AboutScreen : Routes()

    @Serializable
    data object ProfileScreen : Routes()

    @Serializable
    data object EditCheckInHome : Routes()


}



