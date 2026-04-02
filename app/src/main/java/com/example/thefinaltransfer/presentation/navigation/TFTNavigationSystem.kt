package com.example.thefinaltransfer.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.thefinaltransfer.presentation.auth.AuthViewModel
import com.example.thefinaltransfer.presentation.biometricauthentication.BiometricSetupScreen
import com.example.thefinaltransfer.presentation.bottomnavigation.TFTBottomNavigationBar
import com.example.thefinaltransfer.presentation.logins.loginscreen.LoginMethodScreen
import com.example.thefinaltransfer.presentation.logins.mobilelogin.MobileLoginScreen
import com.example.thefinaltransfer.presentation.createpasswordscreen.CreatePasswordScreen
import com.example.thefinaltransfer.presentation.logins.emaillogin.EmailLoginScreen
import com.example.thefinaltransfer.presentation.logins.loginotpscreen.LoginOtpScreen
import com.example.thefinaltransfer.presentation.navoptions.aboutscreen.AboutScreen
import com.example.thefinaltransfer.presentation.navoptions.homescreen.HomeScreen
import com.example.thefinaltransfer.presentation.navoptions.homescreen.functionhome.editcheckin.EditCheckInHomeScreen
import com.example.thefinaltransfer.presentation.navoptions.profilescreen.ProfileScreen
import com.example.thefinaltransfer.presentation.navoptions.uploadscreen.UploadPacketScreen
import com.example.thefinaltransfer.presentation.navoptions.vaultscreen.VaultScreen
import com.example.thefinaltransfer.presentation.signupotpscreen.RegisterOtpScreen
import com.example.thefinaltransfer.presentation.signupscreen.RegisterPersonalDetailsScreen
import com.example.thefinaltransfer.presentation.splash.SplashScreen
import com.example.thefinaltransfer.presentation.startingscreen.StartingScreen

// Routes where Bottom Nav should be VISIBLE
private val bottomNavRoutes = listOf(
    Routes.HomeScreen,
    Routes.VaultScreen,
    Routes.UploadScreen,
    Routes.AboutScreen,
    Routes.ProfileScreen
)

@Composable
fun TFTNavigationSystem() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Shared ViewModel for all auth screens — single source of truth
    val authViewModel: AuthViewModel = viewModel()

    // Show bottom nav only on main app screens
    val showBottomNav = bottomNavRoutes.any { route ->
        currentDestination?.hasRoute(route::class) == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                TFTBottomNavigationBar(
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            startDestination = Routes.SplashScreen,
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(400)
                ) + fadeIn(tween(400))
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(400)
                ) + fadeOut(tween(400))
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(400)
                ) + fadeIn(tween(400))
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(400)
                ) + fadeOut(tween(400))
            }
        ) {


            composable<Routes.SplashScreen> {
                SplashScreen(navController)
            }
            composable<Routes.StartingScreen> {
                StartingScreen(navController)
            }
            composable<Routes.LoginScreen> {
                LoginMethodScreen(navController)
            }
            composable<Routes.EmailLoginScreen> {
                EmailLoginScreen(navController, authViewModel)
            }
            composable<Routes.MobileLoginScreen> {
                MobileLoginScreen(navController, authViewModel)
            }
            composable<Routes.LoginOTPScreen> { backStackEntry ->
                val args = backStackEntry.toRoute<Routes.LoginOTPScreen>()
                LoginOtpScreen(navController, authViewModel, args.identifier, args.method)
            }
            composable<Routes.SignUpScreen> {
                RegisterPersonalDetailsScreen(navController, authViewModel)
            }
            composable<Routes.SignUpOTPScreen> { backStackEntry ->
                val args = backStackEntry.toRoute<Routes.SignUpOTPScreen>()
                RegisterOtpScreen(navController, authViewModel)
            }
            composable<Routes.CreatePasswordScreen> {
                CreatePasswordScreen(navController, authViewModel)
            }
            composable<Routes.BiometricAuthenticationScreen> {
                BiometricSetupScreen(navController)
            }
            composable<Routes.EditCheckInHome> {
                EditCheckInHomeScreen(navController)
            }


            composable<Routes.HomeScreen>(
                enterTransition = { fadeIn(tween(200)) },
                exitTransition = { fadeOut(tween(200)) }
            ) {
                HomeScreen(navController)
            }
            composable<Routes.VaultScreen>(
                enterTransition = { fadeIn(tween(200)) },
                exitTransition = { fadeOut(tween(200)) }
            ) {
                VaultScreen(navController)
            }
            composable<Routes.UploadScreen>(
                enterTransition = { fadeIn(tween(200)) },
                exitTransition = { fadeOut(tween(200)) }
            ) {
                UploadPacketScreen(navController)
            }
            composable<Routes.AboutScreen>(
                enterTransition = { fadeIn(tween(200)) },
                exitTransition = { fadeOut(tween(200)) }
            ) {
                AboutScreen(navController)
            }
            composable<Routes.ProfileScreen>(
                enterTransition = { fadeIn(tween(200)) },
                exitTransition = { fadeOut(tween(200)) }
            ) {
                ProfileScreen(navController)
            }
        }
    }
}