package com.example.thefinaltransfer.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.thefinaltransfer.presentation.biometricauthentication.BiometricSetupScreen
import com.example.thefinaltransfer.presentation.logins.loginscreen.LoginMethodScreen
import com.example.thefinaltransfer.presentation.logins.mobilelogin.MobileLoginScreen
import com.example.thefinaltransfer.presentation.createpasswordscreen.CreatePasswordScreen
import com.example.thefinaltransfer.presentation.logins.emaillogin.EmailLoginScreen
import com.example.thefinaltransfer.presentation.logins.loginotpscreen.LoginOtpScreen
import com.example.thefinaltransfer.presentation.navoptions.aboutscreen.AboutScreen
import com.example.thefinaltransfer.presentation.navoptions.homescreen.HomeScreen
import com.example.thefinaltransfer.presentation.navoptions.profilescreen.ProfileScreen
import com.example.thefinaltransfer.presentation.navoptions.uploadscreen.UploadPacketScreen
import com.example.thefinaltransfer.presentation.navoptions.vaultscreen.VaultScreen
import com.example.thefinaltransfer.presentation.signupotpscreen.RegisterOtpScreen
import com.example.thefinaltransfer.presentation.signupscreen.RegisterPersonalDetailsScreen
import com.example.thefinaltransfer.presentation.splash.SplashScreen
import com.example.thefinaltransfer.presentation.startingscreen.StartingScreen

@Composable
fun TFTNavigationSystem() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavHost(
        startDestination = Routes.HomeScreen,
        navController= navController,
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
    ){

        composable<Routes.SplashScreen> {
            SplashScreen(navController)
        }
        composable<Routes.StartingScreen> {
            StartingScreen(navController)
        }
        composable<Routes.LoginScreen>{
            LoginMethodScreen(navController)
        }
        composable<Routes.EmailLoginScreen> {
            EmailLoginScreen(navController)
        }
        composable<Routes.MobileLoginScreen> {
            MobileLoginScreen(navController)
        }
        composable<Routes.LoginOTPScreen> {
            LoginOtpScreen(navController)
        }
        composable<Routes.SignUpScreen> {
            RegisterPersonalDetailsScreen(navController)
        }
        composable<Routes.SignUpOTPScreen> {
            RegisterOtpScreen(navController)
        }
        composable<Routes.CreatePasswordScreen> {
            CreatePasswordScreen(navController)
        }
        composable<Routes.BiometricAuthenticationScreen> {
            BiometricSetupScreen(navController)
        }


        //These below composable are of the navigation options that are present in the bottom nav bar for smooth transition
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